package com.food.ordering.system.order.service.domain.entity;

import com.food.ordereing.system.domain.entity.BaseEntity;
import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
	private final Product product;
	private final Integer quantity;
	private final Money price;
	private final Money subTotal;
	private OrderId orderId;

	private OrderItem(Builder builder) {
		super.setId(builder.orderItemId);
		product = builder.product;
		quantity = builder.quantity;
		price = builder.price;
		subTotal = builder.subTotal;
	}

	public static Builder builder() {
		return new Builder();
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Money getPrice() {
		return price;
	}

	public Money getSubTotal() {
		return subTotal;
	}

	public static final class Builder {
		private OrderItemId orderItemId;
		private Product product;
		private int quantity;
		private Money price;
		private Money subTotal;

		private Builder() {

		}

		public Builder(Product product, Integer quantity, Money price, Money subTotal) {
			this.product = product;
			this.quantity = quantity;
			this.price = price;
			this.subTotal = subTotal;
		}

		public Builder orderItemId(OrderItemId val) {
			orderItemId = val;
			return this;
		}

		public OrderItem build() {
			return new OrderItem(this);
		}
	}
}
