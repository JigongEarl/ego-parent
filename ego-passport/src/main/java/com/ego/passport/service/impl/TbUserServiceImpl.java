package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbUserServiceImpl implements TbUserService{
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	/**
	 * 登录验证
	 */
	public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
		EgoResult result = new EgoResult();
		TbUser selUser = tbUserDubboServiceImpl.selByUser(user);
		if(selUser != null) {
			result.setStatus(200);
			/*
			 * 将登录信息作为value保存到redis中，key为UUID生成
			 *  --同时将key值保存到cookie中
			 */
			String key = UUID.randomUUID().toString();  
			jedisDaoImpl.set(key, JsonUtils.objectToJson(selUser));
			jedisDaoImpl.expire(key, 60*60*24*7); //设置该值在redis中的生命时长为一周
			CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60*60*24*7); //产生cookie
		}else {
			result.setMsg("用户名或密码错误");
		}
		return result;
	}
	
	/**
	 * 通过token获取用户信息（单点登录核心功能）
	 * token为前端传递的名为“TT_TOKEN”的cookie的值，即保存在redis中用户信息的key
	 */
	public EgoResult getUserInfo(String token, String callback) {
		EgoResult result = new EgoResult();
		String jsonUser = jedisDaoImpl.get(token); //从redis中获取用户信息
		if(jsonUser != null && !jsonUser.equals("")) {
			TbUser user = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
			user.setPassword(null);  //不将密码传到前端，清空
			result.setMsg("OK");
			result.setStatus(200);
			result.setData(user);
		}else {
			result.setMsg("获取用户信息失败");
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * token为前端传递的名为“TT_TOKEN”的cookie的值，即保存在redis中用户信息的key
	 */
	public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
		jedisDaoImpl.delete(token); //从redis中删除登录信息
		CookieUtils.deleteCookie(request, response, "TT_TOKEN"); //清除cookie
		EgoResult result = new EgoResult();
		result.setStatus(200);
		result.setMsg("OK");
		return result;
	}
}
