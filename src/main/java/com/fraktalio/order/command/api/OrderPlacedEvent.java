package com.fraktalio.order.command.api;

import com.fraktalio.api.AuditEntry;

import java.util.List;
import java.util.Objects;

public final class OrderPlacedEvent extends AbstractOrderEvent {

    private final OrderId aggregateIdentifier;
    private final RestaurantId restaurantId;
    private final List<OrderLineItem> orderLineItems;
    private final String deliveryAddress;
    private final String userId;


    public OrderPlacedEvent(AuditEntry auditEntry, OrderId aggregateIdentifier,
                            RestaurantId restaurantId,
                            List<OrderLineItem> orderLineItems, String deliveryAddress, String userId) {
        super(auditEntry);
        this.aggregateIdentifier = aggregateIdentifier;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderPlacedEvent that = (OrderPlacedEvent) o;
        return Objects.equals(aggregateIdentifier, that.aggregateIdentifier) &&
                Objects.equals(restaurantId, that.restaurantId) &&
                Objects.equals(orderLineItems, that.orderLineItems) &&
                Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateIdentifier, restaurantId, orderLineItems, deliveryAddress, userId);
    }
}
