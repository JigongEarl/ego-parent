package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemService;
	
	/**
	 * 分页显示所有商品
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDatagrid show(int page, int rows) {
		return tbItemService.show(page, rows);
	}
	
	/**
	 * 显示商品修改
	 * @return
	 */
	@RequestMapping("/rest/page/item-edit")
	public String showItemEdit() {
		return "item-edit";
	}
	
	/**
	 * 商品删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult result = new EgoResult(); //new一个返回到页面的结果集对象
		int index = tbItemService.updItemStatus(ids, (byte)3);
		if(index == 1) {
			result.setStatus(200);;
		}
		return result;
	}
	
	/**
	 * 商品下架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids) {
		EgoResult result = new EgoResult(); //new一个返回到页面的结果集对象
		int index = tbItemService.updItemStatus(ids, (byte)2);
		if(index == 1) {
			result.setStatus(200);;
		}
		return result;
	}
	
	/**
	 * 商品上架
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids) {
		EgoResult result = new EgoResult();
		int index = tbItemService.updItemStatus(ids, (byte)1);
		if(index == 1) {
			result.setStatus(200);;
		}
		return result;
	}
	
	/**
	 * 新增商品
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public EgoResult insert(TbItem tbItem, String desc, String itemParams) {
		EgoResult result = new EgoResult(); //new一个返回到页面的结果集对象
		int index = 0;
		try {
			index = tbItemService.insTbItemAndDesc(tbItem, desc, itemParams);
			if(index == 1) {  //新增成功，封装status属性值为200
				result.setStatus(200);
			}
		} catch (Exception e) { //新增失败，封装具体失败信息
			result.setData(e.getMessage());
		}
		return result;
	}
}
