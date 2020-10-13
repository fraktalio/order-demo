package com.fraktalio.restaurant.command.api;

import java.util.Objects;
import java.util.UUID;

public final class RestaurantOrderId {

    private final String identifier;

    public RestaurantOrderId() {
        this.identifier = UUID.randomUUID().toString();
    }

    public RestaurantOrderId(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderId orderId = (RestaurantOrderId) o;
        return Objects.equals(identifier, orderId.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
