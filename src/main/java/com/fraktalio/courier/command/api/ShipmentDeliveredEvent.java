package com.fraktalio.courier.command.api;

import java.util.Objects;

public class ShipmentDeliveredEvent {

    private final ShipmentId aggregateIdentifier;

    public ShipmentDeliveredEvent(ShipmentId aggregateIdentifier) {
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
        ShipmentDeliveredEvent that = (ShipmentDeliveredEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
