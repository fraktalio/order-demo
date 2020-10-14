package com.fraktalio.courier.command.api;

import java.util.Objects;
import java.util.UUID;

public final class CourierId {

    private final String identifier;

    public CourierId() {
        this.identifier = UUID.randomUUID().toString();
    }

    public CourierId(String identifier) {
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
        CourierId orderId = (CourierId) o;
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
