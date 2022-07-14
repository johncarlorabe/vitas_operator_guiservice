package com.tlc.sky.pingen.arch.beans.orders;

import java.util.ArrayList;
import java.util.Date;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UIAccount;

public class Order extends Model {
	protected String orderReference;
	protected Date dateRequested;
	protected Date dateApproved;
	protected UIAccount requestedBy;
	protected UIAccount updatedBy;
	protected String status;
	protected int totalBatch;
	protected int totalQuantity;
	protected String totalAmount;
	protected ArrayList<OrderDetails> details;
	public String getOrderReference() {
		return orderReference;
	}
	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}
	public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public UIAccount getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(UIAccount requestedBy) {
		this.requestedBy = requestedBy;
	}
	public UIAccount getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(UIAccount updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalBatch() {
		return totalBatch;
	}
	public void setTotalBatch(int totalBatch) {
		this.totalBatch = totalBatch;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public ArrayList<OrderDetails> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<OrderDetails> details) {
		this.details = details;
	}
	
	
}
