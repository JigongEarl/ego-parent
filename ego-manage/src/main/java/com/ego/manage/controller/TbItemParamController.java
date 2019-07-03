package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamService;
	
	/**
	 * 分页显示规格参数
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDatagrid showParam(int rows, int page) {
		return tbItemParamService.showParam(rows, page);
	}
	
	/**
	 * 批量删除规格参数信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult result = new EgoResult();
		try {
			int index = tbItemParamService.delById(ids);
			if(index == 1) result.setStatus(200);
		} catch (Exception e) {
			result.setData(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据类目id查找规格参数
	 * @param catId
	 * @return
	 */
	@RequestMapping("/item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult showParamByCatId(@PathVariable long catId) {
		return tbItemParamService.showParamByCatId(catId);
	}
	
	/**
	 * 新增规格参数
	 * @param param
	 * @param catId
	 * @return
	 */
	@RequestMapping("/item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam param, @PathVariable long catId) {
		return tbItemParamService.save(param, catId);
	}
}
