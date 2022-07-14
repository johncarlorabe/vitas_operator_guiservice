package com.tlc.sky.pingen.arch.uc.orders;

import com.tlc.sky.pingen.arch.beans.orders.Order;

public abstract class AbstractPendingOrder extends Order{
	public AbstractPendingOrder(int orderId){
		this.setRecordId(orderId);
	}
	
	public abstract boolean approve(String remarks);
	public abstract boolean reject(String reason);

}
