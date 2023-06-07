package com.food.ordering.system.payment.service.domain.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.food.ordereing.system.domain.entity.AggregateRoot;
import com.food.ordereing.system.domain.valueobject.CustomerId;
import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.OrderId;
import com.food.ordereing.system.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.valueobject.PaymentId;

public class Payment extends AggregateRoot<PaymentId> {

	private final OrderId orderId;
	private final CustomerId customerId;
	private final Money price;

	private PaymentStatus paymentStatus;
	private ZonedDateTime createdAt;

	private Payment(Builder builder) {
		setId(builder.paymentId);
		orderId = builder.orderId;
		customerId = builder.customerId;
		price = builder.price;
		paymentStatus = builder.paymentStatus;
		createdAt = builder.createdAt;
	}

	public void initializePayment() {
		setId(new PaymentId(UUID.randomUUID()));
		createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
	}

	public void validatePayment(List<String> failureMessages) {
		if (price == null || !price.isGreaterThanZero()) {
			failureMessages.add("Total price must be greater than zero!");
		}
	}

	public void updateStatus(PaymentStatus paymentStatus) {

	}

	public OrderId getOrderId() {
		return orderId;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public Money getPrice() {
		return price;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public static final class Builder {
		private final OrderId orderId;
		private final CustomerId customerId;
		private final Money price;
		private PaymentId paymentId;
		private PaymentStatus paymentStatus;
		private ZonedDateTime createdAt;

		public Builder(OrderId orderId, CustomerId customerId, Money price) {
			this.orderId = orderId;
			this.customerId = customerId;
			this.price = price;
		}

		public Builder id(PaymentId val) {
			paymentId = val;
			return this;
		}

		public Builder paymentStatus(PaymentStatus val) {
			paymentStatus = val;
			return this;
		}

		public Builder createdAt(ZonedDateTime val) {
			createdAt = val;
			return this;
		}

		public Payment build() {
			return new Payment(this);
		}
	}
}
