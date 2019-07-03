package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemDescServiceImpl implements TbItemDescService {
	@Reference
	private TbItemDescDubboService TbItemDescDubboServiceImpl;
	@Value("${redis.desc.key}")
	private String descKey;
	@Resource
	private JedisDao jedisDaoImpl;
	/**
	 * 显示商品详细信息
	 */
	public String showDesc(long itemId) {
		/*
		 * 判断redis是否有缓存，如果有就从redis中取出，否则从数据库取出并保存到redis
		 */
		String key = descKey + itemId;
		String desc;
		if(jedisDaoImpl.exists(key)) {
			desc = jedisDaoImpl.get(key);
			if(desc != null && !desc.equals("")) {
				return desc;
			}
		}
		/*
		 * 从数据库中查找
		 */
		TbItemDesc iTemDesc = TbItemDescDubboServiceImpl.selByItemId(itemId);
		desc = iTemDesc.getItemDesc();
		//保存到redis
		jedisDaoImpl.set(key, desc);
		System.out.println("*************商品详细信息***********"+desc);
		return desc;
	}

}
