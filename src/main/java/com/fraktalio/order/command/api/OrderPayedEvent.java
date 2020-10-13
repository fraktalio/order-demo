package com.fraktalio.order.command.api;

import java.util.Objects;

public final class OrderPayedEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;

    public OrderPayedEvent(AuditEntry auditEntry, OrderId aggregateIdentifier) {
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
        OrderPayedEvent that = (OrderPayedEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier);
    }
}
