 package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserController {
	@Resource
	private TbUserService tbUserServiceImpl;
	/**
	 * 显示登陆页面
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/showLogin")
	public String showLogin(@RequestHeader(value = "Referer", defaultValue = "") String url, 
							Model model, String interurl) {
		//注意：url默认默认值为空，如果是重定向的请求，请求内容中将没有referer请求头，也就不会给url赋值
		//Model用于后端向前端传值的对象，是请求中的默认参数，不需要自行实例化，ModelAndView需要自行实例化
		System.out.println("url:"+url);
		System.out.println("interurl:"+interurl);
		if(interurl != null && !interurl.equals("")) {//购物车拦截器重定向的请求，记录来源地址
			model.addAttribute("redirect", interurl);
		}else if(!url.equals("")){  //url不为空表示为从其他页面请求来的，记录Referer信息
			model.addAttribute("redirect", url);
		}
		return "login";
	}
	
	/**
	  * 登录
	 * @param tbUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
		return tbUserServiceImpl.login(user, request, response);
	}
	
	/**
	 * 通过token获取用户信息（单点登录核心功能）,有可能为跨域请求
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserInfo(@PathVariable String token, String callback) {
		EgoResult result = tbUserServiceImpl.getUserInfo(token, callback);
		/*
		 * 有回调函数名表示为跨域请求，采用jsonp返回结果
		 */
		if(callback !=null && !callback.equals("")) {
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		//非跨域请求，直接返回结果
		return result;
	}
	
	/**
	 * 退出登录
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response) {
		EgoResult result = tbUserServiceImpl.logout(token, request, response);
		/*
		 * 有回调函数名表示为跨域请求，采用jsonp返回结果
		 */
		if(callback !=null && !callback.equals("")) {
			MappingJacksonValue mjv = new MappingJacksonValue(result);
			mjv.setJsonpFunction(callback);
			return mjv;
		}
		//非跨域请求，直接返回结果
		return result;
	}
	
	@RequestMapping("/user/showRegister")
	public String showRegister() {
		return "register";
	}
}
