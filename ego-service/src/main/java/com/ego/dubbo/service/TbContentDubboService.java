package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 分页查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDatagrid selContentByPage(long categoryId, int page, int rows);
	
	/**
	 * 新增数据
	 * @param tbContent
	 * @return
	 */
	int insContent(TbContent tbContent);
	
	/**
	 * 查找出最近的前count个
	 * @param count
	 * @param isSort
	 * @return
	 */
	List<TbContent> selByCount(int count, boolean isSort);
}
