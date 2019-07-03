package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamItem;
/**
 * dubbo服务接口--->针对TbItem的操作，显示商品
 * @author 86156
 *
 */
public interface TbItemDubboService {
	/**
	 * 商品分页显示
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDatagrid show(int page, int rows);
	
	/**
	 * 根据id修改商品状态
	 * @param tbItem
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	
	/**
	 * 新增商品
	 * @param tbItem
	 * @return
	 */
	int insTbItem(TbItem tbItem);
	
	/**
	 * 新增商品时，同步新增商品描述及商品规格
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	int insTbItemAndDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception;
	
	/**
	 * 根据状态查询可用商品信息
	 * @param status
	 * @return
	 */
	List<TbItem> selAllByStatus(byte status);
	
	/**
	 * 根据id查找商品信息
	 * @param id
	 * @return
	 */
	TbItem selById(long id);
}
