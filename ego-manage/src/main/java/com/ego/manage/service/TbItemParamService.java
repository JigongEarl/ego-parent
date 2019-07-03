package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
	/**
	 * 显示所有商品的规格参数
	 * @param tbItemParam
	 * @return
	 */
	EasyUIDatagrid showParam(int rows, int page);
	
	/**
	 * 批量删除规格参数信息
	 * @param tbItemParam
	 * @return
	 */
	int delById(String ids) throws Exception;
	
	/**
	 * 根据类目id查找参数模板
	 * @param catId
	 * @return
	 */
	EgoResult showParamByCatId(long catId);
	
	/**
	 * 新增参数模板信息
	 * @param param
	 * @return
	 */
	EgoResult save(TbItemParam param, long catId);
}
