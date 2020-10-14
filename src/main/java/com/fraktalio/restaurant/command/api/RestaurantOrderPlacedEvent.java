package com.fraktalio.restaurant.command.api;

import java.util.Objects;

public final class RestaurantOrderPlacedEvent {

    private final RestaurantOrderId restaurantOrderId;

    public RestaurantOrderPlacedEvent(RestaurantOrderId restaurantOrderId) {
        this.restaurantOrderId = restaurantOrderId;
    }

    public RestaurantOrderId getRestaurantOrderId() {
        return restaurantOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderPlacedEvent that = (RestaurantOrderPlacedEvent) o;
        return Objects.equals(restaurantOrderId, that.restaurantOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantOrderId);
    }
}
