package com.ego.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.portal.service.TbContentService;

@Controller
public class TbContentController {
	
	@Resource
	private TbContentService tbContentServiceImpl;
	
	@RequestMapping("showPic")
	//@ResponseBody
	public String showPic(Model model) {
		model.addAttribute("ad1", tbContentServiceImpl.showPic());
		return "index";
	}
	
}
