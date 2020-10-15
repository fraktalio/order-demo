package com.fraktalio.courier.command.api;

import java.util.Objects;

public class ShipmentExpiredEvent {

    private final ShipmentId aggregateIdentifier;

    public ShipmentExpiredEvent(ShipmentId aggregateIdentifier) {
        this.aggregateIdentifier = aggregateIdentifier;
    }

    public ShipmentId getAggregateIdentifier() {
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
        ShipmentExpiredEvent that = (ShipmentExpiredEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
