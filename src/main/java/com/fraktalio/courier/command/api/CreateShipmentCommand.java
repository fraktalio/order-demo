package com.fraktalio.courier.command.api;

import java.util.Objects;

public final class CreateShipmentCommand {

    private final ShipmentId targetAggregateIdentifier;
    private final Address address;

    public CreateShipmentCommand(ShipmentId targetAggregateIdentifier, Address address) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
        this.address = address;
    }

    public ShipmentId getTargetAggregateIdentifier() {
        return targetAggregateIdentifier;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateShipmentCommand that = (CreateShipmentCommand) o;
        return Objects.equals(targetAggregateIdentifier, that.targetAggregateIdentifier) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier, address);
    }
}
