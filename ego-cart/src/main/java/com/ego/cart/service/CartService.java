package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

public interface CartService {

	/**
	 * 添加购物车
	 * @param id 商品id
	 * @param num 商品数量
	 */
	void addCart(long id, int num, HttpServletRequest request);
	
	/**
	 * 显示购物车信息
	 */
	List<TbItemChild> showCart(HttpServletRequest request);
	
	/**
	 * 根据id修改数量
	 * @param id
	 * @param num
	 * @return
	 */
	EgoResult update(long id,int num,HttpServletRequest request) ;
	
	/**
	 * 删除购物车商品
	 * @param id
	 * @param req
	 * @return
	 */
	EgoResult delete(long id,HttpServletRequest request);
}
