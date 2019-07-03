package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemDescService;

@Controller
public class TbItemDescController {
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	/**
	 * 显示商品详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/item/desc/{itemId}.html", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String showDesc(@PathVariable long itemId) {
		System.out.println();
		return tbItemDescServiceImpl.showDesc(itemId);
	}
}
