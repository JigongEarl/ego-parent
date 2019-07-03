package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCatService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCatController {
	@Resource
	private TbContentCatService tbContentCatServiceImpl;
	
	/**
	 * 显示内容分类类目树
	 * @param pid
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITree> showContentCat(@RequestParam(defaultValue = "0") long id){
		return tbContentCatServiceImpl.showContentCat(id);
	}
	
	/**
	 * 新建分类
	 * @param cat
	 * @return
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory cat) {
		return tbContentCatServiceImpl.create(cat);
	}
	
	/**
	 * 更新分类
	 * @param cat
	 * @return
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory cat) {
		return tbContentCatServiceImpl.update(cat);
	}
	
	/**
	 * 删除分类
	 * @param cat
	 * @return
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory cat) {
		return tbContentCatServiceImpl.delete(cat);
	}
}
