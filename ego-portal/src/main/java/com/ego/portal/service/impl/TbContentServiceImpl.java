package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigpic.key}") //获取redis属性文件中指定key的值
	private String key;
	/**
	 * 显示大广告
	 * @return
	 */
	public String showPic() {
		/*
		 * 判断redis中是否存在，如果存在直接从redis中获取
		 * 否则查询数据库，并将查询结果保存到redis中
		 */
		if(jedisDaoImpl.exists(key)) {
			String value = jedisDaoImpl.get(key);
			if(value != null && !value.equals("")) {
				return value;
			}
		}
		
		List<TbContent> list = tbContentDubboServiceImpl.selByCount(6, true); //降序排序，显示六张图片
		/*
		 * 将查询结果封装成map类型的数组中，前端格式：
		 * [{"srcB":"http://***.jpg",
		 * "height":240,
		 * "alt":"",
		 * "width":670,
		 * "src":"http://***.jpg",
		 * "widthB":550,
		 * "href":"http://**","heightB":240}, {.....}, {....}}
		 */
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for(TbContent tbContent : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("href", tbContent.getUrl());
			map.put("src", tbContent.getPic());
			map.put("height", 240);
			map.put("width", 670);
			map.put("srcB", tbContent.getPic2());
			map.put("heightB", 240);
			map.put("widthB", 550);
			map.put("alt", "对不起，图片加载失败");
			mapList.add(map);
		}
		String JsonList = JsonUtils.objectToJson(mapList);
		//将查询结果备份到redis中缓存
		jedisDaoImpl.set(key, JsonList);
		
		return JsonList;
	}
}
