package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.JedisCluster;
@Repository
public class JedisDaoImpl implements JedisDao{
	@Resource
	private JedisCluster jedisClients;
	
	/**
	 * 判断key是否存在
	 */
	public Boolean exists(String key) {
		return jedisClients.exists(key);
	}
	
	/**
	 * 获取值
	 */
	public String get(String key) {
		return jedisClients.get(key);
	}
	
	/**
	 * 设置值
	 */
	public String set(String key, String value) {
		return jedisClients.set(key, value);
	}
	
	/**
	 * 删除
	 */
	public Long delete(String key) {
		return jedisClients.del(key);
	}
	
	/**
	 * 设置数据保存时长
	 */
	public Long expire(String key, int seconds) {
		return jedisClients.expire(key, seconds);
	}
}
