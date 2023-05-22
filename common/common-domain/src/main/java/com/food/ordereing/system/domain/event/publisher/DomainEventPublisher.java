package com.food.ordereing.system.domain.event.publisher;

import com.food.ordereing.system.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

	void publish(T domainEvent);
}
