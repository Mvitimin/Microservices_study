package com.food.ordering.system.order.service.data.access.order.mapper;

import static com.food.ordering.system.order.service.data.access.order.entity.OrderEntity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.food.ordereing.system.domain.valueobject.CustomerId;
import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.OrderId;
import com.food.ordereing.system.domain.valueobject.ProductId;
import com.food.ordereing.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.data.access.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.data.access.order.entity.OrderEntity;
import com.food.ordering.system.order.service.data.access.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

@Component
public class OrderDataAccessMapper {

	public OrderEntity orderToOrderEntity(Order order) {
		OrderEntity orderEntity = OrderEntity.builder()
			.id(order.getId().getValue())
			.customerId(order.getCustomerId().getValue())
			.restaurantId(order.getRestaurantId().getValue())
			.trackingId(order.getTrackingId().getValue())
			.address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
			.price(order.getPrice().getAmount())
			.items(orderItemsToOrderItemEntities(order.getItems()))
			.orderStatus(order.getOrderStatus())
			.failureMessages(order.getFailureMessages() != null ?
				String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "")
			.build();
		orderEntity.getAddress().setOrder(orderEntity);
		orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
		return orderEntity;
	}

	public Order orderEntityToOrder(OrderEntity orderEntity) {
		return Order.builder()
			.orderId(new OrderId(orderEntity.getId()))
			.customerId(new CustomerId(orderEntity.getCustomerId()))
			.restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
			.deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
			.price(new Money(orderEntity.getPrice()))
			.items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
			.trackingId(new TrackingId(orderEntity.getTrackingId()))
			.orderStatus(orderEntity.getOrderStatus())
			.failureMessages(orderEntity.getFailureMessages().isEmpty() ? List.of()
				: new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER))))
			.build();
	}

	private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
		return new StreetAddress(
			address.getId(),
			address.getStreet(),
			address.getPostalCode(),
			address.getCity()
		);
	}

	private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> orderItems) {
		return orderItems.stream().map(orderItem -> OrderItemEntity.builder()
				.id(orderItem.getId().getValue())
				.productId(orderItem.getProduct().getId().getValue())
				.price(orderItem.getPrice().getAmount())
				.subTotal(orderItem.getSubTotal().getAmount())
				.quantity(orderItem.getQuantity())
				.build())
			.collect(Collectors.toList());
	}

	private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItemEntities) {
		return orderItemEntities.stream()
			.map(orderItemEntity -> OrderItem.builder()
				.orderItemId(new OrderItemId(orderItemEntity.getId()))
				.product(new Product(new ProductId(orderItemEntity.getProductId())))
				.price(new Money(orderItemEntity.getPrice()))
				.quantity(orderItemEntity.getQuantity())
				.subtotal(new Money(orderItemEntity.getPrice()))
				.build())
			.collect(Collectors.toList());
	}

	private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
		return OrderAddressEntity.builder()
			.id(deliveryAddress.getId())
			.city(deliveryAddress.getCity())
			.street(deliveryAddress.getStreet())
			.postalCode(deliveryAddress.getPostalCode())
			.build();
	}
}
