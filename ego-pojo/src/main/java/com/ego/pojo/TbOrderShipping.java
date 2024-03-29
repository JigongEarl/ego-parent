package com.ego.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbOrderShipping implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.order_id
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_name
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_phone
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_mobile
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_state
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_city
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_district
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverDistrict;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_address
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.receiver_zip
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private String receiverZip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.created
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private Date created;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_order_shipping.updated
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    private Date updated;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.order_id
     *
     * @return the value of tb_order_shipping.order_id
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.order_id
     *
     * @param orderId the value for tb_order_shipping.order_id
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_name
     *
     * @return the value of tb_order_shipping.receiver_name
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_name
     *
     * @param receiverName the value for tb_order_shipping.receiver_name
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_phone
     *
     * @return the value of tb_order_shipping.receiver_phone
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_phone
     *
     * @param receiverPhone the value for tb_order_shipping.receiver_phone
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_mobile
     *
     * @return the value of tb_order_shipping.receiver_mobile
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_mobile
     *
     * @param receiverMobile the value for tb_order_shipping.receiver_mobile
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_state
     *
     * @return the value of tb_order_shipping.receiver_state
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverState() {
        return receiverState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_state
     *
     * @param receiverState the value for tb_order_shipping.receiver_state
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState == null ? null : receiverState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_city
     *
     * @return the value of tb_order_shipping.receiver_city
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_city
     *
     * @param receiverCity the value for tb_order_shipping.receiver_city
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_district
     *
     * @return the value of tb_order_shipping.receiver_district
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_district
     *
     * @param receiverDistrict the value for tb_order_shipping.receiver_district
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict == null ? null : receiverDistrict.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_address
     *
     * @return the value of tb_order_shipping.receiver_address
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_address
     *
     * @param receiverAddress the value for tb_order_shipping.receiver_address
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.receiver_zip
     *
     * @return the value of tb_order_shipping.receiver_zip
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public String getReceiverZip() {
        return receiverZip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.receiver_zip
     *
     * @param receiverZip the value for tb_order_shipping.receiver_zip
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip == null ? null : receiverZip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.created
     *
     * @return the value of tb_order_shipping.created
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.created
     *
     * @param created the value for tb_order_shipping.created
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_order_shipping.updated
     *
     * @return the value of tb_order_shipping.updated
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_order_shipping.updated
     *
     * @param updated the value for tb_order_shipping.updated
     *
     * @mbggenerated Fri Apr 26 01:59:00 CST 2019
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

	@Override
	public String toString() {
		return "TbOrderShipping [orderId=" + orderId + ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", receiverMobile=" + receiverMobile + ", receiverState=" + receiverState
				+ ", receiverCity=" + receiverCity + ", receiverDistrict=" + receiverDistrict + ", receiverAddress="
				+ receiverAddress + ", receiverZip=" + receiverZip + ", created=" + created + ", updated=" + updated
				+ "]";
	}
    
}