package com.fraktalio.order.command.api;

import java.util.Objects;

public final class OrderRejectedEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;

    public OrderRejectedEvent(AuditEntry auditEntry, OrderId aggregateIdentifier) {
        super(auditEntry);
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
        OrderRejectedEvent that = (OrderRejectedEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
