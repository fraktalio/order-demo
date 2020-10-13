package com.fraktalio.order.command.api;

import java.util.List;
import java.util.Objects;

public final class OrderDeliveredEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;
    private final RestaurantId restaurantId;
    private final List<OrderLineItem> orderLineItems;
    private final String deliveryAddress;

    public OrderDeliveredEvent(AuditEntry auditEntry, OrderId aggregateIdentifier,
                               RestaurantId restaurantId,
                               List<OrderLineItem> orderLineItems, String deliveryAddress) {
        super(auditEntry);
        this.aggregateIdentifier = aggregateIdentifier;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public OrderId getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDeliveredEvent that = (OrderDeliveredEvent) o;
        return aggregateIdentifier.equals(that.aggregateIdentifier) &&
                Objects.equals(restaurantId, that.restaurantId) &&
                Objects.equals(orderLineItems, that.orderLineItems) &&
                Objects.equals(deliveryAddress, that.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier, restaurantId, orderLineItems, deliveryAddress);
    }
}
