package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

public interface TbItemCatDubboService {
	/**
	 * 查找所有分类
	 * @param pid 父科目id值
	 * @return
	 */
	public List<TbItemCat> show(long pid);
	
	/**
	 * 查找指定id值的分类数据
	 * @param id
	 * @return
	 */
	public TbItemCat selById(long id);
}
