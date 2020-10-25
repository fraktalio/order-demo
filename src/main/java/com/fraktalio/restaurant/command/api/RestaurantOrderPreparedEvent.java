package com.fraktalio.restaurant.command.api;

import java.util.Objects;

public final class RestaurantOrderPreparedEvent {

    private RestaurantOrderId aggregateIdentifier;

    public RestaurantOrderPreparedEvent(RestaurantOrderId aggregateIdentifier) {
        this.aggregateIdentifier = aggregateIdentifier;
    }

    private RestaurantOrderPreparedEvent() {
    }

    public RestaurantOrderId getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderPreparedEvent that = (RestaurantOrderPreparedEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
