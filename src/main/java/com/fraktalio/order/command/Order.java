package com.fraktalio.order.command;

import com.fraktalio.order.command.api.OrderId;
import com.fraktalio.order.command.api.OrderState;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate(snapshotTriggerDefinition = "orderSnapshotTriggerDefinition", cache = "cache")
class Order {

    @AggregateIdentifier
    private OrderId id;
    private OrderState orderState;

    private Order() {
    }
}
