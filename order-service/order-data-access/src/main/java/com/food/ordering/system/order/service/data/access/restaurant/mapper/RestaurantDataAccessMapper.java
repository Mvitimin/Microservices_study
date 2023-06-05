package com.food.ordering.system.order.service.data.access.restaurant.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.food.ordereing.system.domain.valueobject.Money;
import com.food.ordereing.system.domain.valueobject.ProductId;
import com.food.ordereing.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.data.access.restaurant.exception.RestaurantDataAccessException;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;

@Component
public class RestaurantDataAccessMapper {

	public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
		return restaurant.getProducts().stream()
			.map(product -> product.getId().getValue())
			.collect(Collectors.toList());
	}

	public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
		RestaurantEntity restaurantEntity =
			restaurantEntities.stream().findFirst().orElseThrow(() ->
				new RestaurantDataAccessException("Restaurant could not be found!"));

		List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
			new Product(new ProductId(entity.getProductId()), entity.getProductName(),
				new Money(entity.getProductPrice()))).toList();

		return Restaurant.builder()
			.restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
			.products(restaurantProducts)
			.active(restaurantEntity.getRestaurantActive())
			.build();
	}
}
