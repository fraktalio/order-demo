package com.fraktalio.order.command.api;

import com.fraktalio.api.AuditEntry;

import java.util.Objects;

public final class OrderExpiredEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;

    public OrderExpiredEvent(AuditEntry auditEntry, OrderId aggregateIdentifier) {
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
        OrderExpiredEvent that = (OrderExpiredEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
