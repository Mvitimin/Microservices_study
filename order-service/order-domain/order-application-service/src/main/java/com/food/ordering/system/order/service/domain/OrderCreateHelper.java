package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateHelper {

	private final OrderDomainService orderDomainService;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final RestaurantRepository restaurantRepository;
	private final OrderDataMapper orderDataMapper;
	private final ApplicationDomainEventPublisher applicationDomainEventPublisher;


	@Transactional
	public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
		checkCustomer(createOrderCommand.getCustomerId());
		Restaurant restaurant = checkRestaurant(createOrderCommand);
		Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
		Order orderResult = saveOrder(order);
		log.info("Order is created with id: {}", orderResult.getId().getValue());
		applicationDomainEventPublisher.publish(orderCreatedEvent);
		return orderCreatedEvent;

	}

	private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
		Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
		Optional<Restaurant> restaurantOptional = restaurantRepository.findRestaurantInformation(restaurant);
		if (restaurantOptional.isEmpty()) {
			log.warn("Could not find restaurant with id: {}", createOrderCommand.getRestaurantId());
			throw new OrderDomainException(
				"Could not find restaurant with id: " + createOrderCommand.getRestaurantId());
		}
		return restaurantOptional.get();
	}

	private void checkCustomer(UUID customerId) {
		Optional<Customer> customer = customerRepository.findCustomer(customerId);
		if (customer.isEmpty()) {
			log.warn("Could not find customer with customer id: {}", customerId);
			throw new OrderDomainException("Could not find customer with the customer id: " + customerId);
		}
	}

	private Order saveOrder(Order order) {
		Order orderResult = orderRepository.save(order);
		if (orderResult == null) {
			throw new OrderDomainException("Could not save order!");
		}
		log.info("Order is saved with id: {}", order.getId());
		return orderResult;
	}
}
