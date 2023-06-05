package com.food.ordering.system.order.service.data.access.customer.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.food.ordering.system.order.service.data.access.customer.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

	private final CustomerJpaRepository customerJpaRepository;
	private final CustomerDataAccessMapper customerDataAccessMapper;

	@Override
	public Optional<Customer> findCustomer(UUID customerId) {
		return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
	}
}
