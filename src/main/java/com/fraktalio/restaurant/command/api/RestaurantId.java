package com.fraktalio.restaurant.command.api;

import java.util.Objects;
import java.util.UUID;

public final class RestaurantId {

    private final String identifier;

    public RestaurantId() {
        this.identifier = UUID.randomUUID().toString();
    }

    public RestaurantId(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String identifier() {
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
        RestaurantId orderId = (RestaurantId) o;
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
