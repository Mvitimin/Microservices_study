package com.food.ordering.system.payment.service.domain.event;

import java.time.ZonedDateTime;
import java.util.List;

import com.food.ordereing.system.domain.event.DomainEvent;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class PaymentEvent implements DomainEvent<Payment> {

	private final Payment payment;
	private final ZonedDateTime zonedDateTime;
	private final List<String> failureMessages;
}
