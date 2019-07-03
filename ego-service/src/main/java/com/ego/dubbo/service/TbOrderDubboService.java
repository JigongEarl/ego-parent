package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderDubboService {
	
	/**
	 * 创建订单
	 * @param order
	 * @param orderItem
	 * @param Shiping
	 * @return
	 * @throws Exception 
	 */
	int insOrder(TbOrder order, List<TbOrderItem> Items, TbOrderShipping Shiping) throws Exception;
}
