package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService {
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;
	/**
	 * 按id查询商品信息
	 */
	public TbItemChild showItemDetails(long id) {
		/*
		 * 如果redis中已有缓存，从redis中取，否则从数据库中查找并将结果保留到redis中
		 */
		String key = itemKey + id;
		if(jedisDaoImpl.exists(key)) {
			String childStr = jedisDaoImpl.get(key);
			if(childStr != null && !childStr.equals("")) {
				return JsonUtils.jsonToPojo(childStr, TbItemChild.class);
			}
		}
		//从数据库中查找
		TbItem item = tbItemDubboServiceImpl.selById(id);
		/*
		 * 封装TbItemChild对象
		 */
		TbItemChild child = new TbItemChild();
		child.setId(item.getId());
		child.setTitle(item.getTitle());
		child.setPrice(child.getPrice());
		child.setSellPoint(child.getSellPoint());
		child.setImages(item.getImage()!=null&&!item.equals("") ? item.getImage().split(",") : new String[1]);
		//缓存到redis中
		jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		
		return child;
	}
}
