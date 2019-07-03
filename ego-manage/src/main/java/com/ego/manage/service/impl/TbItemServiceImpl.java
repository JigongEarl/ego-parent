package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Value("${redis.bigpic.key}")
	private String itemKey;  
	@Value("${search.url}")
	private String url;  //solr	控制器请求路径
	@Reference
	private TbItemDubboService tbItemDubboService;
	@Resource
	private JedisDao jedisDaoImpl;
	
	/**
	 * 显示所有商品条目
	 */
	public EasyUIDatagrid show(int page, int rows) {
		return tbItemDubboService.show(page, rows);
	}
	
	/**
	 * 更新商品状态
	 * 如删除、下架、上架
	 */
	public int updItemStatus(String ids, byte status) {
		int index = 0;
		String[] idStr = ids.split(",");
		for(String id : idStr) {
			TbItem tbItem = new TbItem();
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			index += tbItemDubboService.updItemStatus(tbItem);
			if(status == 2 || status == 3) {
				jedisDaoImpl.delete(itemKey + id);
			}
		}
		if(index == ids.length()) {
			return 1;
		}
		return 0;
	}

	/**
	 * 新增商品
	 * -->其中商品描述信息单独在一张数据库表格中，该方法同时更新了两张表格，底层实现了事务管理
	 */
	public int insTbItemAndDesc(TbItem tbItem, String desc, String itemParams) throws Exception {
		/*
		 * 匿名内部类重写方法要求属性为Final或者全局属性
		 */
		final TbItem finalTbItem = tbItem;
		final String finalDesc = desc;
		
		long id = IDUtils.genItemId();
		Date date = new Date();
		/*
		 * 封装tbItem对象
		 */
		tbItem.setId(id);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItem.setStatus((byte)1);
		/*
		 * 封装TbItemDesc对象
		 */
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		/*
		  * 封装TbItemParam对象
		 */
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(id);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		
		int index = tbItemDubboService.insTbItemAndDesc(tbItem, tbItemDesc, tbItemParamItem);
		/*
		  * 将数据同步更新到solr,由于同步更新较慢，为改善用户体验，以新线程独立运行
		 */
		new Thread() {
			public void run() {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("item", finalTbItem);
				param.put("desc", finalDesc);
				//url写在配置文件中
				//HttpClientUtil.doPostJson("http://localhost:8083/solr/add", JsonUtils.objectToJson(param));
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(param));
			};
		}.start();
		
		return index;
	}
	
}
