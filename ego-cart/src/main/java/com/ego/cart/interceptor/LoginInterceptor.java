package com.ego.cart.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
/**
 * 拦截器，拦截添加购物车请求，判断是否已经登陆
 * @author 86156
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	@Resource
	private JedisDao jedisDaoImpl;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		System.out.println("token:"+token);
		if(token != null && !token.equals("")) {
			TbUser user = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
			System.out.println("user:"+user.toString());
			if(user != null && !user.equals("")) {
				return true;
			}
		}
		/*
		 * 还没有登录，将重定向到登陆页面
		 * >由于重定向不包含referer请求头，需要将登陆成功后跳转的地址(interurl)加到重定向地址中，
		 * >所以登录控制器也需要判定请求来源是否为重定向的
		 */
		String num = request.getParameter("num");
		response.sendRedirect("http://localhost:8084/user/showLogin/?interurl="
							+ request.getRequestURL() + "%3Fnum=" + num);
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
