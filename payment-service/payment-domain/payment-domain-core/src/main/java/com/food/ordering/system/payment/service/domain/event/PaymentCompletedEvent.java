package com.food.ordering.system.payment.service.domain.event;

import java.time.ZonedDateTime;
import java.util.List;

import com.food.ordering.system.payment.service.domain.entity.Payment;

public class PaymentCompletedEvent extends PaymentEvent {

	public PaymentCompletedEvent(Payment payment,
		ZonedDateTime zonedDateTime, List<String> failureMessages) {
		super(payment, zonedDateTime, failureMessages);
	}
}
