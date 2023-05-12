package com.food.ordering.system.order.service.domain.entity;

import java.util.List;

import com.food.ordereing.system.domain.entity.AggregateRoot;
import com.food.ordereing.system.domain.valueobject.RestaurantId;

public class Restaurant extends AggregateRoot<RestaurantId> {
	private final List<Product> products;
	private boolean active;

	private Restaurant(Builder builder) {
		super.setId(builder.restaurantId);
		products = builder.products;
		active = builder.active;
	}

	public static Builder builder() {
		return new Builder();
	}

	public List<Product> getProducts() {
		return products;
	}

	public boolean isActive() {
		return active;
	}

	public static final class Builder {
		private RestaurantId restaurantId;
		private List<Product> products;
		private boolean active;

		private Builder() {

		}

		public Builder restaurantId(RestaurantId restaurantId) {
			this.restaurantId = restaurantId;
			return this;
		}

		public Builder active(boolean val) {
			active = val;
			return this;
		}

		public Restaurant build() {
			return new Restaurant(this);
		}
	}
}