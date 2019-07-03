package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TbContentService {
	/**
	 * 分页查询
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDatagrid showContent(long categoryId, int page, int rows);
	
	/**
	 * 新增数据
	 * @param tbContent
	 * @return
	 */
	EgoResult save(TbContent tbContent);
	
}
