package com.food.ordering.system.order.service.data.access.customer.mapper;

import org.springframework.stereotype.Component;

import com.food.ordereing.system.domain.valueobject.CustomerId;
import com.food.ordering.system.order.service.data.access.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.domain.entity.Customer;

@Component
public class CustomerDataAccessMapper {

	public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
		return new Customer(new CustomerId(customerEntity.getId()));
	}
}
