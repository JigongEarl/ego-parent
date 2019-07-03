package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 新增商品描述信息
	 * @param desc
	 * @return
	 */
	int insTbItemDesc(TbItemDesc tbItemDesc);
	
	/**
	 * 按照商品id查找商品描述
	 * @param itemId
	 * @return
	 */
	public TbItemDesc selByItemId(long itemId);
}
