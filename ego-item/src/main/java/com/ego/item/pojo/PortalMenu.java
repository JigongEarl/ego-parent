package com.ego.item.pojo;

import java.util.List;

/**
 * 门户导航菜单商品分类数据，Potal最终要的数据格式
 * @author 86156
 *
 */
public class PortalMenu {
	
	private List<Object> data;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PortalMenu [data=" + data + "]";
	}
	
}
