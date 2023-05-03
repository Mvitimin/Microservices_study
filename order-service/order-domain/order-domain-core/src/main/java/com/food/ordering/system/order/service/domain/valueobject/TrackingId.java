package com.food.ordering.system.order.service.domain.valueobject;

import java.util.UUID;

import com.food.ordereing.system.domain.valueobject.BaseId;

public class TrackingId extends BaseId<UUID> {
	protected TrackingId(UUID value) {
		super(value);
	}
}