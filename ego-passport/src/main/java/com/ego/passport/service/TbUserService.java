package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 通过token获取用户信息（单点登录核心功能）
	 * @param token
	 * @param callback
	 * @return
	 */
	EgoResult getUserInfo(String token, String callback);
	
	/**
	 * 退出登录
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response);
}
