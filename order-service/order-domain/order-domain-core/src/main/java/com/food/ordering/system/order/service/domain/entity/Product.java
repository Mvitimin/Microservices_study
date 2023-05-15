package com.food.ordering.system.order.service.domain.entity;

import com.food.ordereing.system.domain.entity.BaseEntity;
import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
	private String name;
	private Money price;

	public Product(ProductId productId, String name, Money price) {
		super.setId(productId);
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public void updateWithConfirmedNameAndPrice(String name, Money price) {
		this.name = name;
		this.price = price;

	}
}
