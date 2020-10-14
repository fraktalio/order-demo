package com.fraktalio.courier.command.api;

import java.util.Objects;
import java.util.UUID;

public final class ShipmentId {

    private final String identifier;

    public ShipmentId() {
        this.identifier = UUID.randomUUID().toString();
    }

    public ShipmentId(String identifier) {
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
        ShipmentId orderId = (ShipmentId) o;
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
