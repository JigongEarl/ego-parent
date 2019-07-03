package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

public interface TbItemService {
	/**
	 * 按id查询商品信息
	 * @param id
	 * @return
	 */
	public TbItemChild showItemDetails(long id);
}
