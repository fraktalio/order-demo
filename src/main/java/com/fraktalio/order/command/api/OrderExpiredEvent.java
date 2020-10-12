package com.fraktalio.order.command.api;

import java.util.Objects;

public final class OrderExpiredEvent {

    private final OrderId aggregateIdentifier;

    public OrderExpiredEvent(OrderId aggregateIdentifier) {
        this.aggregateIdentifier = aggregateIdentifier;
    }

    public OrderId getAggregateIdentifier() {
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
        OrderExpiredEvent that = (OrderExpiredEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
