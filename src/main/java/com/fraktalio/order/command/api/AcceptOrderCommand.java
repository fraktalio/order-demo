package com.fraktalio.order.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class AcceptOrderCommand {

    @TargetAggregateIdentifier
    private OrderId targetAggregateIdentifier;

    public AcceptOrderCommand(OrderId targetAggregateIdentifier) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
    }

    private AcceptOrderCommand() {
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
        AcceptOrderCommand that = (AcceptOrderCommand) o;
        return targetAggregateIdentifier.equals(that.targetAggregateIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier);
    }
}
