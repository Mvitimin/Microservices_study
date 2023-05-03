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
}
