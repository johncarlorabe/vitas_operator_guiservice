package com.tlc.sky.pingen.arch.uc.orders;

import com.tlc.sky.pingen.arch.beans.orders.Order;

public abstract class AbstractNewOrder extends Order{
	public AbstractNewOrder(String orderReference){
		this.orderReference = orderReference;
	}
	public abstract boolean register();
}
