package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

/**
 * 内容分类管理模块
 * @author 86156
 *
 */
public interface TbContentCatDubboService {
	
	/**
	 * 按父id查询所有子类目
	 * @param pid
	 * @return
	 */
	List<TbContentCategory> selByPid(long pid);
	
	/**
	 * 插入新内容分类
	 * @param cat
	 * @return
	 */
	int insTbContentCat(TbContentCategory cat);
	
	/**
	 * 更新记录
	 * @param id
	 * @return
	 */
	int updById(TbContentCategory cat);
	
	/**
	 * 按id查询类目
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
}
