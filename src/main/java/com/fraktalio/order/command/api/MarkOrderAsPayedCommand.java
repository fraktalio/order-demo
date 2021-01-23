package com.fraktalio.order.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public final class MarkOrderAsPayedCommand {

    @TargetAggregateIdentifier
    private OrderId targetAggregateIdentifier;

    public MarkOrderAsPayedCommand(OrderId targetAggregateIdentifier) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
    }

    private MarkOrderAsPayedCommand() {
    }

    public OrderId getTargetAggregateIdentifier() {
        return targetAggregateIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkOrderAsPayedCommand that = (MarkOrderAsPayedCommand) o;
        return targetAggregateIdentifier.equals(that.targetAggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier);
    }
}
