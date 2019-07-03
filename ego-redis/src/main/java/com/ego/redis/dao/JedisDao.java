package com.ego.redis.dao;

public interface JedisDao {
	/**
	 * 判断Key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	String set(String key, String value);

	/**
	 * 删除值
	 * @param key
	 */
	Long delete(String key);
	
	/**
	 * 设置数据保存时长
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);
}
