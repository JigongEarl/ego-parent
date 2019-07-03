package com.ego.item.pojo;

import java.util.List;
/**
 * 门户导航菜单商品分类数据
 * @author 86156
 *
 */
public class PortalMenuNode {
	
	private String u;
	private String n;
	private List<Object> i;
	
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u = u;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public List<Object> getI() {
		return i;
	}
	public void setI(List<Object> i) {
		this.i = i;
	}
	@Override
	public String toString() {
		return "PortalMenuNode [u=" + u + ", n=" + n + ", i=" + i + "]";
	}
	
}
