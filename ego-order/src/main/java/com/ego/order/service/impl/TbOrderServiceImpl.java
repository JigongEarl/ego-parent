package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
@Service
public class TbOrderServiceImpl implements TbOrderService{
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${cart.key}")
	private String cartKey;
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	/**
	 * 显示订单内容
	 */
	public List<TbItemChild> showOrder(List<Long> ids, HttpServletRequest request) {
		//从cookie中获取redis中用户信息的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//用户信息
		TbUser user = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
		//redis中购物车信息
		String key = cartKey + user.getUsername();
		List<TbItemChild> cartList = JsonUtils.jsonToList(jedisDaoImpl.get(key), TbItemChild.class);
		List<TbItemChild> list = new ArrayList<TbItemChild>();
		for(TbItemChild child : cartList) {
			for(Long id : ids) {
				if((long)child.getId() == (long)id) {
					//判断购买量是否大于库存
					TbItem tbItem = tbItemDubboServiceImpl.selById(id);
					if(child.getNum() > tbItem.getNum()) {
						child.setEnough(false);
					}else {
						child.setEnough(true);
					}
					list.add(child);
				}
			}
		}
		return list;
	}
	
	/**
	 * 新建订单
	 */
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {
		/*
		 * 封装订单表数据
		 */
		TbOrder order = new TbOrder();
		order.setPayment(param.getPayment());
		order.setPaymentType(param.getPaymentType());
		long id = IDUtils.genItemId();
		order.setOrderId(id+"");
		Date date =new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//当前登录用户的信息
		TbUser user = JsonUtils.jsonToPojo(
				jedisDaoImpl.get(CookieUtils.getCookieValue(request, "TT_TOKEN")), TbUser.class);
		order.setUserId(user.getId());
		order.setBuyerNick(user.getUsername());
		order.setBuyerRate(0);
		/*
		 * 封装订单商品表数据
		 */
		for (TbOrderItem  item : param.getOrderItems()) {
			item.setId(IDUtils.genItemId()+"");
			item.setOrderId(id+"");
		}
		/*
		 * 封装用户表信息（tb_order_shipping表）
		 */
		TbOrderShipping shipping = param.getOrderShipping();
		shipping.setOrderId(id+"");
		shipping.setCreated(date);
		shipping.setUpdated(date);
		/*
		 * 封装返回结果
		 */
		EgoResult erResult = new EgoResult();
		try {
			int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
			if(index>0){
				erResult.setStatus(200);
				//删除购买的商品
				String cartJson = jedisDaoImpl.get(cartKey+user.getUsername());
				List<TbItemChild> cartList = JsonUtils.jsonToList(cartJson, TbItemChild.class);
				/*
				 * 利用迭代器遍历删除集合中的元素
				 */
//				Iterator<TbItemChild> it = cartList.iterator();
//				if(it.hasNext()) {
//					TbItemChild child = it.next();
//					for (TbOrderItem item : param.getOrderItems()) {
//						if(child.getId().longValue()==Long.parseLong(item.getItemId())){
//							it.remove(); //删除
//						}
//					}
//				}
				//利用新建集合删除原集合中的元素
				List<TbItemChild> newList = new ArrayList<>();
				for (TbItemChild child : cartList) {
					for (TbOrderItem item : param.getOrderItems()) {
						if(child.getId().longValue()==Long.parseLong(item.getItemId())){
							newList.add(child);
						}
					}
				}
				for (TbItemChild mynew : newList) {
					cartList.remove(mynew);
				}
				//更新购物车
				jedisDaoImpl.set(cartKey+user.getUsername(), JsonUtils.objectToJson(cartList));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return erResult;
	}
	
	
}
