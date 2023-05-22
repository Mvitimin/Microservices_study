package com.food.ordering.system.order.service.domain.ports.output.meesage.publisher.restaurantapproval;

import com.food.ordereing.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
