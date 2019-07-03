package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试控制类，开发时访问WEB-INF下的页面
 * @author 86156
 *
 */
@Controller
public class PageController {
	
	@RequestMapping("/")
	public String welcome() {
		return "forward:/showPic";
	} 
	
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
