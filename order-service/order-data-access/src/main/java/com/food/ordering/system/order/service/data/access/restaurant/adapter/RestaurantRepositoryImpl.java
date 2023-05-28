package com.food.ordering.system.order.service.data.access.restaurant.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.data.access.restaurant.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.order.service.data.access.restaurant.repository.RestaurantJapRepository;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

	private final RestaurantJapRepository restaurantJapRepository;
	private final RestaurantDataAccessMapper restaurantDataAccessMapper;

	@Override
	public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
		List<UUID> restaurantProducts =
			restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
		Optional<List<RestaurantEntity>> restaurantEntities = restaurantJapRepository.findByRestaurantIdAndProductId(
			restaurant.getId().getValue(), restaurantProducts);
		return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
	}
}
