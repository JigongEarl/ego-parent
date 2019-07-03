package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 查询商品功能-->页面信息类
 * @author 86156
 *
 */
public class EasyUIDatagrid implements Serializable{
	private List<?> rows;  //每页的所有信息
	private long total;    //总记录数
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "EasyUIDatagrid [rows=" + rows + ", total=" + total + "]";
	}
}
