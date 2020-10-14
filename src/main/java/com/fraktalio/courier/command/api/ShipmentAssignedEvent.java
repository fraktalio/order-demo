package com.fraktalio.courier.command.api;

import java.util.Objects;

public class ShipmentAssignedEvent {
    private final ShipmentId aggregateIdentifier;
    private final CourierId courierId;

    public ShipmentAssignedEvent(ShipmentId aggregateIdentifier, CourierId courierId) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.courierId = courierId;
    }

    public ShipmentId getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    public CourierId getCourierId() {
        return courierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentAssignedEvent that = (ShipmentAssignedEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier) &&
                Objects.equals(courierId, that.courierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier, courierId);
    }
}
