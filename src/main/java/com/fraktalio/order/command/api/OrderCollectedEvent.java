package com.fraktalio.order.command.api;

import com.fraktalio.api.AuditEntry;

import java.util.Objects;

public final class OrderCollectedEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderCollectedEvent that = (OrderCollectedEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }

    public OrderCollectedEvent(AuditEntry auditEntry, OrderId aggregateIdentifier) {
        super(auditEntry);
        this.aggregateIdentifier = aggregateIdentifier;
    }

    public OrderId getAggregateIdentifier() {
        return aggregateIdentifier;
    }
}
