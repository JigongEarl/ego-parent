package com.ego.manage.service;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

public interface TbItemService {
	
	/**
	 * 显示所有商品条目
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDatagrid show(int page, int rows);
	
	/**
	 * 修改商品状态
	 * @param ids
	 * @param status
	 * @return
	 */
	int updItemStatus(String ids, byte status);
	
	/**
	 * 新增商品时，同步新增商品描述
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	int insTbItemAndDesc(TbItem tbItem, String desc, String itemParams) throws Exception;
}
