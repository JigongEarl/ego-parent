package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCatDubboService;
import com.ego.manage.service.TbContentCatService;
import com.ego.pojo.TbContentCategory;
@Service
public class TbContentCatServiceImpl implements TbContentCatService {
	
	@Reference  //自动注入，Dubbo容器管理的注释，用于消费端，表明使用的是服务端的什么服务
	private TbContentCatDubboService tbContentCatDubboServiceImpl;
	
	/**
	 * 显示内容分类类目树
	 */
	public List<EasyUITree> showContentCat(long id) {
		List<EasyUITree> listTree =  new ArrayList<EasyUITree>();
		/*
		 * 基于查找的类目结果，封装EasyUITree集合，集合中每一个对象与EasyUI树控件中节点一一对应，
		 * 包含了其显示所需信息
		 */
		List<TbContentCategory> listCat = tbContentCatDubboServiceImpl.selByPid(id);
		for(TbContentCategory cat : listCat) {
			EasyUITree tree = new EasyUITree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent() ? "closed" : "open");//父节点状态关闭，子节点状态为展开
			listTree.add(tree);
		}
		return listTree;
	}
	
	/**
	 * 新建分类
	 */
	public EgoResult create(TbContentCategory cat) {
		EgoResult result = new EgoResult();
		/*
		 * 判断同一父目录下，新建分类是否已经存在
		 */
		List<TbContentCategory> brothers = tbContentCatDubboServiceImpl.selByPid(cat.getParentId());
		for(TbContentCategory brother : brothers) {
			if(brother.getName().equals(cat.getName())) {
				result.setData("错误：该分类已经存在");
				return result;
			}
		}
		/*
		 * 封装cat对象
		 */
		long id = IDUtils.genItemId();
		cat.setId(id);
		Date date = new Date();
		cat.setCreated(date);
		cat.setUpdated(date);
		cat.setSortOrder(1);
		cat.setStatus(1);
		cat.setIsParent(false);
		//新增数据,返回是否成功
		int index = tbContentCatDubboServiceImpl.insTbContentCat(cat);
		/*
		 * 新增成功后，更新父目录状态(主要更新isParent属性)
		 */
		if(index > 0) {
			TbContentCategory parent = new TbContentCategory();
			parent.setIsParent(true);
			parent.setId(cat.getParentId());//by id更新
			tbContentCatDubboServiceImpl.updById(parent);
		}
		/*
		 * 设定返回对象
		 */
		result.setStatus(200);
		Map<String, Long> map = new HashMap<>();
		map.put("id", id);
		result.setData(map);
		
		return result;
	}
	
	/**
	 * 更新分类
	 */
	public EgoResult update(TbContentCategory cat) {
		EgoResult result = new EgoResult();
		TbContentCategory nowCat = tbContentCatDubboServiceImpl.selById(cat.getId());
		/*
		 * 判断同一父目录下，更新的分类是否已经存在
		 */
		List<TbContentCategory> brothers = tbContentCatDubboServiceImpl.selByPid(nowCat.getParentId());
		for(TbContentCategory brother : brothers) {
			if(brother.getName().equals(cat.getName())) {
				result.setData("错误：该分类已经存在");
				return result;
			}
		}
		//更新分类信息
		int index = tbContentCatDubboServiceImpl.updById(cat);
		
		if(index > 0) {
			result.setStatus(200);
		}
		return result;
	}

	/**
	 * 删除分类
	 */
	public EgoResult delete(TbContentCategory cat) {
		EgoResult result = new EgoResult();
		TbContentCategory category = tbContentCatDubboServiceImpl.selById(cat.getId());
		category.setStatus(2); //status值为2表示该分类已删除
		int index = tbContentCatDubboServiceImpl.updById(category);
		
		/*
		 * 删除成功后，判断是否为父节点
		 * 1、为父节点-->查询所有子节点-->遍历子节点，status更新为2
		 * 2、不是父节点-->判断是否有兄弟节点 ？ 不做操作 ：将父节点is_parent变为false
		 */
		if(index > 0) {  //index=1
			if(category.getIsParent()) {
				List<TbContentCategory> children = tbContentCatDubboServiceImpl.selByPid(cat.getId());
				for(TbContentCategory child : children) {
					child.setStatus(2);
					tbContentCatDubboServiceImpl.updById(child);
				}
			}else {
				List<TbContentCategory> brothers = tbContentCatDubboServiceImpl.selByPid(category.getParentId());
				if(brothers == null || brothers.size() == 0) {
					TbContentCategory parent = new TbContentCategory();
					parent.setId(cat.getParentId());
					parent.setIsParent(false);
					tbContentCatDubboServiceImpl.updById(parent);
				}
			}
			result.setStatus(200);
		}
		return result;
	}

}
