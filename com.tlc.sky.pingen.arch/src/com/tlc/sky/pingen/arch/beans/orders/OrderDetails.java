package com.tlc.sky.pingen.arch.beans.orders;

import java.util.Date;

import com.tlc.gui.modules.common.Model;

public class OrderDetails extends Model {
	protected int orderId;
	protected String batchNumber;
	protected int quantity;
	protected String value;
	protected Date dateInserted;
	protected String amount;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
