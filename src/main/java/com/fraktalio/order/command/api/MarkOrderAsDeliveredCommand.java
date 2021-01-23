package com.fraktalio.order.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public final class MarkOrderAsDeliveredCommand {

    @TargetAggregateIdentifier
    private OrderId targetAggregateIdentifier;

    public MarkOrderAsDeliveredCommand(OrderId targetAggregateIdentifier) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
    }

    private MarkOrderAsDeliveredCommand() {
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
        MarkOrderAsDeliveredCommand that = (MarkOrderAsDeliveredCommand) o;
        return targetAggregateIdentifier.equals(that.targetAggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier);
    }
}
