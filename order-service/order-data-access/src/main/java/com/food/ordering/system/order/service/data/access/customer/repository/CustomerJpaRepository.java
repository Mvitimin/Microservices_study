package com.food.ordering.system.order.service.data.access.customer.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.ordering.system.order.service.data.access.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.domain.entity.Customer;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {

	Optional<Customer> findCustomerById(UUID customerId);
}

