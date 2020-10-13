package com.fraktalio.restaurant.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public final class PlaceRestaurantOrderCommand {

    @TargetAggregateIdentifier
    private final RestaurantId targetAggregateIdentifier;
    private final RestaurantOrderDetails orderDetails;
    private final RestaurantOrderId restaurantOrderId;

    public PlaceRestaurantOrderCommand(RestaurantId targetAggregateIdentifier,
                                       RestaurantOrderDetails orderDetails,
                                       RestaurantOrderId restaurantOrderId) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
        this.orderDetails = orderDetails;
        this.restaurantOrderId = restaurantOrderId;
    }

    public RestaurantId getTargetAggregateIdentifier() {
        return targetAggregateIdentifier;
    }

    public RestaurantOrderDetails getOrderDetails() {
        return orderDetails;
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
        PlaceRestaurantOrderCommand that = (PlaceRestaurantOrderCommand) o;
        return Objects.equals(targetAggregateIdentifier, that.targetAggregateIdentifier) &&
                Objects.equals(orderDetails, that.orderDetails) &&
                Objects.equals(restaurantOrderId, that.restaurantOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier, orderDetails, restaurantOrderId);
    }
}
