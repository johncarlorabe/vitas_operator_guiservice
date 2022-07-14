package com.tlc.sky.pingen.arch.uc.sku;

import com.tlc.sky.pingen.arch.beans.Product;

public abstract class AbstractRegisteredProduct extends Product{
	public abstract boolean exists();
	public abstract boolean activate(String reason);
	public abstract boolean deactivate(String reason);
}
