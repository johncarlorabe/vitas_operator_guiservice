package com.ibayad.product.management.m;

import com.tlc.gui.modules.common.Model;

public class Products extends Model{
	
	protected String type;
	protected String category;
	protected String name;
	protected String sku;
	protected long amount;
	protected String description;
	protected String productinterface;
	protected String id;
	protected String provider;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductinterface() {
		return productinterface;
	}
	public void setProductinterface(String productinterface) {
		this.productinterface = productinterface;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}
