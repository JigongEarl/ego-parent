package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCatService {
	/**
	 * 显示内容分类类目树
	 * @param pid
	 * @return
	 */
	List<EasyUITree> showContentCat(long pid);
	
	/**
	 * 新建分类
	 * @param name
	 * @param pid
	 * @return
	 */
	EgoResult create(TbContentCategory cat);
	
	/**
	 * 更新分类
	 * @param cat
	 * @return
	 */
	EgoResult update(TbContentCategory cat);
	
	/**
	 * 删除分类
	 * @param cat
	 * @return
	 */
	EgoResult delete(TbContentCategory cat);
}
