package com.food.ordering.system.order.service.domain.entity;

import com.food.ordereing.system.domain.entity.AggregateRoot;
import com.food.ordereing.system.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

	public Customer() {
	}

	public Customer(CustomerId customerId) {
		super.setId(customerId);
	}
}
