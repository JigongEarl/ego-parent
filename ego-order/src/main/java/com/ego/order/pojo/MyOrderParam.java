package com.ego.order.pojo;

import java.util.List;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class MyOrderParam {
	private int paymentType;
	private List<TbOrderItem> orderItems;
	private String payment;
	private TbOrderShipping orderShipping;
	
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	@Override
	public String toString() {
		return "MyOrderParam [paymentType=" + paymentType + ", orderItems=" + orderItems + ", payment=" + payment
				+ ", orderShipping=" + orderShipping + "]";
	}
	
}
