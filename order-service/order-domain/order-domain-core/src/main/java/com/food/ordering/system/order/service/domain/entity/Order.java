package com.food.ordering.system.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.food.ordereing.system.domain.entity.AggregateRoot;
import com.food.ordereing.system.domain.valueobject.CustomerId;
import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.OrderId;
import com.food.ordereing.system.domain.valueobject.OrderStatus;
import com.food.ordereing.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

public class Order extends AggregateRoot<OrderId> {
	private final CustomerId customerId;
	private final RestaurantId restaurantId;
	private final StreetAddress streetAddress;
	private final Money price;
	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessage;

	public void initializeOrder() {
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();

	}

	public void validateOrder() {
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();

	}

	private void validateItemsPrice() {
		Money orderItemsTotal = items.stream().map(orderItem -> {
			validateItemPrice(orderItem);
			return orderItem.getSubTotal();
		}).reduce(Money.ZERO, Money::add);

		if (!price.equals(orderItemsTotal)) {
			throw new OrderDomainException("Total price:  " + price.getAmount() + "is not equal to Order items total: "
				+ orderItemsTotal.getAmount() + "!");
		}
	}

	private void validateItemPrice(OrderItem orderItem) {
		if (!orderItem.isPriceValid()) {
			throw new OrderDomainException(
				"Order item price: " + orderItem.getPrice().getAmount() + "is not valid for product"
					+ orderItem.getProduct().getId().getValue());
		}
	}

	private void validateTotalPrice() {
		if (price == null || !price.isGreaterThanZero()) {
			throw new OrderDomainException("Total price must be greater than zero!");
		}
	}

	private void validateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException("Order is not in correct state for initialization!");
		}

	}

	private void initializeOrderItems() {
		long itemId = 1;
		for (OrderItem orderItem : items) {
			orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
		}
	}

	private Order(Builder builder) {
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		streetAddress = builder.streetAddress;
		price = builder.price;
		items = builder.items;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessage = builder.failureMessage;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getStreetAddress() {
		return streetAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getFailureMessage() {
		return failureMessage;
	}

	public static final class Builder {
		private final CustomerId customerId;
		private final RestaurantId restaurantId;
		private final StreetAddress streetAddress;
		private final Money price;
		private final List<OrderItem> items;
		private OrderId orderId;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessage;

		public Builder(CustomerId customerId, RestaurantId restaurantId, StreetAddress streetAddress, Money price,
			List<OrderItem> items) {
			this.customerId = customerId;
			this.restaurantId = restaurantId;
			this.streetAddress = streetAddress;
			this.price = price;
			this.items = items;
		}

		public Builder id(OrderId val) {
			orderId = val;
			return this;
		}

		public Builder trackingId(TrackingId val) {
			trackingId = val;
			return this;
		}

		public Builder orderStatus(OrderStatus val) {
			orderStatus = val;
			return this;
		}

		public Builder failureMessage(List<String> val) {
			failureMessage = val;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}
}
