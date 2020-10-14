package com.fraktalio.order.command;

import com.fraktalio.order.command.api.AcceptOrderCommand;
import com.fraktalio.order.command.api.AuditEntry;
import com.fraktalio.order.command.api.ExceptionStatusCode;
import com.fraktalio.order.command.api.MarkOrderAsCollectedCommand;
import com.fraktalio.order.command.api.MarkOrderAsDeliveredCommand;
import com.fraktalio.order.command.api.MarkOrderAsExpiredCommand;
import com.fraktalio.order.command.api.MarkOrderAsPayedCommand;
import com.fraktalio.order.command.api.MarkOrderAsPreparedCommand;
import com.fraktalio.order.command.api.OrderAcceptedEvent;
import com.fraktalio.order.command.api.OrderCollectedEvent;
import com.fraktalio.order.command.api.OrderDeliveredEvent;
import com.fraktalio.order.command.api.OrderExpiredEvent;
import com.fraktalio.order.command.api.OrderId;
import com.fraktalio.order.command.api.OrderLineItem;
import com.fraktalio.order.command.api.OrderPayedEvent;
import com.fraktalio.order.command.api.OrderPlacedEvent;
import com.fraktalio.order.command.api.OrderPreparedEvent;
import com.fraktalio.order.command.api.OrderRejectedEvent;
import com.fraktalio.order.command.api.OrderState;
import com.fraktalio.order.command.api.PlaceOrderCommand;
import com.fraktalio.order.command.api.RejectOrderCommand;
import com.fraktalio.order.command.api.RestaurantId;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "orderSnapshotTriggerDefinition", cache = "cache")
class Order {

    @AggregateIdentifier
    private OrderId id;
    private OrderState orderState;

    private RestaurantId restaurantId;
    private List<OrderLineItem> orderLineItems;
    private String deliveryAddress;

    /**
     * This default constructor is used by the Axon Repository to construct a prototype
     * [Order]. Events are then used to set properties such as the
     * OrderId in order to make the Aggregate reflect it's true logical state.
     */
    private Order() {
    }

    /**
     * This constructor is marked as a 'CommandHandler' for the
     * [PlaceOrderCommand]. This command can be used to construct new
     * instances of the Aggregate. If successful a new [OrderPlacedEvent]
     * is 'applied' to the aggregate using the Axon 'apply' method. The apply method
     * appears to also propagate the Event to any other registered 'Event
     * Listeners', who may take further action.
     *
     * @param command    - the command
     * @param auditEntry - the authority who initiated this command
     */
    @CommandHandler
    Order(PlaceOrderCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        apply(new OrderPlacedEvent(auditEntry,
                                   command.getTargetAggregateIdentifier(),
                                   command.getRestaurantId(),
                                   command.getOrderLineItems(),
                                   command.getDeliveryAddress(),
                                   auditEntry.getWho()));
    }

    @EventSourcingHandler
    void on(OrderPlacedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.PLACED;
        restaurantId = event.getRestaurantId();
        orderLineItems = event.getOrderLineItems();
        deliveryAddress = event.getDeliveryAddress();
    }

    @CommandHandler
    void on(RejectOrderCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.PLACED == orderState) {
            apply(new OrderRejectedEvent(auditEntry, command.getTargetAggregateIdentifier()));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_PLACED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderRejectedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.REJECTED;
    }

    @CommandHandler
    void on(AcceptOrderCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.PLACED == orderState) {
            apply(new OrderAcceptedEvent(auditEntry, command.getTargetAggregateIdentifier()));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_PLACED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderAcceptedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.ACCEPTED;
    }

    @CommandHandler
    void on(MarkOrderAsPreparedCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.ACCEPTED == orderState) {
            apply(new OrderPreparedEvent(auditEntry,
                                         command.getTargetAggregateIdentifier(),
                                         restaurantId,
                                         orderLineItems,
                                         deliveryAddress));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_ACCEPTED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderPreparedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.PREPARED;
    }

    @CommandHandler
    void on(MarkOrderAsCollectedCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.PREPARED == orderState) {
            apply(new OrderCollectedEvent(auditEntry, command.getTargetAggregateIdentifier()));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_PREPARED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderCollectedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.COLLECTED;
    }

    @CommandHandler
    void on(MarkOrderAsDeliveredCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.COLLECTED == orderState) {
            apply(new OrderDeliveredEvent(auditEntry,
                                          command.getTargetAggregateIdentifier(),
                                          restaurantId,
                                          orderLineItems,
                                          deliveryAddress));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_COLLECTED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderDeliveredEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.DELIVERED;
    }

    @CommandHandler
    void on(MarkOrderAsPayedCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        if (OrderState.DELIVERED == orderState) {
            apply(new OrderPayedEvent(auditEntry, command.getTargetAggregateIdentifier()));
        } else {
            throw new UnsupportedOperationException(ExceptionStatusCode.ORDER_NOT_DELIVERED.name());
        }
    }

    @EventSourcingHandler
    void on(OrderPayedEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.PAYED;
    }

    @CommandHandler
    void on(MarkOrderAsExpiredCommand command, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        apply(new OrderExpiredEvent(auditEntry, command.getTargetAggregateIdentifier()));
    }

    @EventSourcingHandler
    void on(OrderExpiredEvent event) {
        id = event.getAggregateIdentifier();
        orderState = OrderState.EXPIRED;
    }

    @ExceptionHandler(resultType = UnsupportedOperationException.class)
    void handle(UnsupportedOperationException exception) {
        var statusCode = ExceptionStatusCode.valueOf(exception.getMessage());
        throw new CommandExecutionException(statusCode.getDescription(), exception, statusCode);
    }
}
