package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	
	@Reference  //自动注入，Dubbo容器管理的注释，用于消费端，表明使用的是服务端的什么服务
	private TbItemCatDubboService tbItemCatDubboService;
	
	/**
	 * 显示商品类目树
	 */
	public List<EasyUITree> show(long pid) {
		//查找指定pid的所有类目
		List<TbItemCat> listCat = tbItemCatDubboService.show(pid);
		/*
		 * 基于查找的类目结果，封装EasyUITree集合，集合中每一个对象与EasyUI树控件中节点一一对应，
		 * 包含了其显示所需信息
		 */
		List<EasyUITree> listTree = new ArrayList<>();
		for(TbItemCat cat:listCat) {
			EasyUITree tree = new EasyUITree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent() ? "closed" : "open");//父节点状态关闭，子节点状态为展开
			listTree.add(tree);
		}
		return listTree;
	}
	
	
}
