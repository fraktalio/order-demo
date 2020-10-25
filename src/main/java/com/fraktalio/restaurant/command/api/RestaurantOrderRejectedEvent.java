package com.fraktalio.restaurant.command.api;

import java.util.Objects;

public final class RestaurantOrderRejectedEvent {

    private RestaurantOrderId aggregateIdentifier;
    private String reason;

    public RestaurantOrderRejectedEvent(RestaurantOrderId aggregateIdentifier, String reason) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.reason = reason;
    }

    private RestaurantOrderRejectedEvent() {
    }

    public RestaurantOrderId getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderRejectedEvent that = (RestaurantOrderRejectedEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier, reason);
    }
}
