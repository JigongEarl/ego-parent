package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	/**
	 * 查询门户导航菜单商品分类数据，需要调用包含递归的sellAllMenu()方法
	 */
	public PortalMenu showCatMenu() {
		//查询出所有一级类目菜单
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		PortalMenu pm = new PortalMenu();
		pm.setData(sellAllMenu(list));
		return pm;
	}
	/**
	 * 递归查询--所有商品分类数据
	 * @param list
	 * @return
	 */
	private List<Object> sellAllMenu(List<TbItemCat> list) {
		List<Object> listNode = new ArrayList<>();
		//遍历一级类目菜单
		for(TbItemCat cat : list) {
			/*
			 * 判断是否有子类目，赋值u，n，i,其中i递归查询子类目
			 */
			if(cat.getIsParent()) {
				PortalMenuNode pmd = new PortalMenuNode();
				//"u":"/products/1.html"; "n":"<a href='/products/1.html'>图书、音像、电子书刊</a>"
				pmd.setU("/products/"+cat.getId()+".html");
				pmd.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
				pmd.setI(sellAllMenu(tbItemCatDubboServiceImpl.show(cat.getId())));
				listNode.add(pmd);
			}else {
				listNode.add("/products/"+cat.getId()+".html|"+cat.getName());
			}
		}
		return listNode;
	}
	
}
