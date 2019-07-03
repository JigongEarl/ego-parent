package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {
	@Reference
	private TbItemParamDubboService tbItemParamDubboService;
	@Reference
	private TbItemCatDubboService tbItemCatDubboService;
	/**
	 * 显示所有商品的规格参数
	 */
	public EasyUIDatagrid showParam(int rows, int page) {
		//查询商品规格参数信息（不含商品类目名称）
		EasyUIDatagrid datagrid = tbItemParamDubboService.showParam(rows, page);
		
		/*
		 * 将datagrid中的"Rows"从TbItemParam替换成TbItemParamChild对象集合，含商品类目名称
		 */
		List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
		List<TbItemParamChild> childList = new ArrayList<>();
		for(TbItemParam param : list) {
			/*
			 * 封装TbItemParamChild对象，比TbItemParam对象多了一个ItemCatName属性
			 */
			TbItemParamChild child = new TbItemParamChild();
			child.setId(param.getId());
			child.setItemCatId(param.getItemCatId());
			child.setParamData(param.getParamData());
			child.setCreated(param.getCreated());
			child.setUpdated(param.getUpdated());
			//封装ItemCatName属性，值从TbItemCat表中查询得到
			child.setItemCatName(tbItemCatDubboService.selById(param.getItemCatId()).getName());
			
			childList.add(child);
		}
		//将包含了ItemCatName信息的TbItemParamChild对象集合封装到datagrid对象中，替代TbItemParam对象集合
		datagrid.setRows(childList);
		return datagrid;
	}

	/**
	 * 批量删除规格参数信息
	 */
	public int delById(String ids) throws Exception {
		return tbItemParamDubboService.delById(ids);
	}
	
	/**
	 * 根据类目id查找规格参数
	 */
	public EgoResult showParamByCatId(long catId) {
		EgoResult result = new EgoResult();
		TbItemParam param = tbItemParamDubboService.selByCatId(catId);
		if(param != null) {
			result.setStatus(200);
			result.setData(param);
		}
		return result;
	}
	
	/**
	 * 新增规格参数
	 */
	public EgoResult save(TbItemParam param, long catId) {
		Date data = new Date();
		param.setCreated(data);
		param.setUpdated(data);
		param.setItemCatId(catId);
		//param.setId(id);不需要setId，因为数据库中已设置id自增
		int index = tbItemParamDubboService.insParam(param);
		
		EgoResult result = new EgoResult();
		if(index == 1) { //新增成功
			result.setStatus(200);
		}
		return result;
	}

}
