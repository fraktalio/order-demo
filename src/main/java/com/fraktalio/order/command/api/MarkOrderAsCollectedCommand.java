package com.fraktalio.order.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public final class MarkOrderAsCollectedCommand {

    @TargetAggregateIdentifier
    private OrderId targetAggregateIdentifier;

    public MarkOrderAsCollectedCommand(OrderId targetAggregateIdentifier) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
    }

    private MarkOrderAsCollectedCommand() {
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
        MarkOrderAsCollectedCommand that = (MarkOrderAsCollectedCommand) o;
        return targetAggregateIdentifier.equals(that.targetAggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier);
    }
}
