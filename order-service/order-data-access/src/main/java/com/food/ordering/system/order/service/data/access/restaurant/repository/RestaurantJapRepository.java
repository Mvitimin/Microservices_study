package com.food.ordering.system.order.service.data.access.restaurant.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntityId;

public interface RestaurantJapRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {

	Optional<List<RestaurantEntity>> findByRestaurantIdAndProductId(UUID restaurantId, List<UUID> productIds);

}
