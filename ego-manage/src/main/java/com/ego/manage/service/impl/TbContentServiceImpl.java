package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService {
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigpic.key}")
	private String key;
	
	/**
	 * 分页显示大广告详细列表
	 */
	public EasyUIDatagrid showContent(long categoryId, int page, int rows) {
		return tbContentDubboServiceImpl.selContentByPage(categoryId, page, rows);
	}
	
	/**
	 * 新增大广告
	 * 考虑到新增的大广告要用于前台显示，新增时需要同步更新到redis缓存中
	 */
	public EgoResult save(TbContent tbContent) {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		//向数据库中新增
		int index = tbContentDubboServiceImpl.insContent(tbContent);
		/*
		 *判断redis中是否有缓存，没有就添加到redis中 
		 */
		if(jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if(value != null && !value.equals("")) {
				List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);
				/*
				 * 将新增加的大图片数据按照前端json格式要求封装到map中，然后放入list
				 */
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("srcB", tbContent.getPic2());
				map.put("height", 240);
				map.put("alt", "对不起，图片加载失败");
				map.put("width", 670);
				map.put("src", tbContent.getPic());
				map.put("widthB", 550);
				map.put("href", tbContent.getUrl());
				map.put("heightB", 240);
				/*
				 * 保证最多只有六张图片(前台大图片为六张)，如果缓存中已经有六张，将最后一张删除
				 */
				if(list.size() == 6) {   
					list.remove(5);  
				}
				list.add(0, map);//新图片插入到redis缓存的List，放到第一张位置
				jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
			}
		}
		EgoResult result = new EgoResult();
		if(index > 0) {
			result.setStatus(200);
		}
		return result;
	}
	
}
