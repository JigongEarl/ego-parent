package com.ego.search.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.TbItemChild;
import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	/**
	 * 初始化solr数据
	 * @param model
	 * @return
	 */
	// 注解中的proceduces属性，指定返回值类型及返回数据编码方式
	@RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String init(Model model) {
		try { // 统计初始化数据耗时
			long start = System.currentTimeMillis();
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			String time = "初始化总时长" + (end - start) / 1000 + "秒";
			System.out.println(time);
			return time;
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return "初始化失败";
		}
	}

	/**
	 * 搜索功能
	 * 
	 * @param model
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search.html")
	public String search(Model model, String q, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "12") int rows) {
		try {
			q = new String(q.getBytes("iso-8859-1"), "utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("query", q);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
	
	/**
	 * 新增，后台新增时同步数据
	 * @param map
	 * @return
	 */
	@RequestMapping("solr/add")
	@ResponseBody
	public int add(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		System.out.println(map.get("item"));
		try {
			Map<String, Object>_map = (HashMap)map.get("item");
			String desc = map.get("desc").toString();
			return tbItemServiceImpl.add(map);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
