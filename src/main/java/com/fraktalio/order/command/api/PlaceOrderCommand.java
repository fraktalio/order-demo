package com.fraktalio.order.command.api;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;
import java.util.Objects;

public final class PlaceOrderCommand {

    @TargetAggregateIdentifier
    private final OrderId targetAggregateIdentifier;
    private final RestaurantId restaurantId;
    private final List<OrderLineItem> orderLineItems;
    private final String deliveryAddress;


    public PlaceOrderCommand(OrderId targetAggregateIdentifier, RestaurantId restaurantId,
                             List<OrderLineItem> orderLineItems, String deliveryAddress) {
        this.targetAggregateIdentifier = targetAggregateIdentifier;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public OrderId getTargetAggregateIdentifier() {
        return targetAggregateIdentifier;
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
        PlaceOrderCommand that = (PlaceOrderCommand) o;
        return targetAggregateIdentifier.equals(that.targetAggregateIdentifier) &&
                Objects.equals(restaurantId, that.restaurantId) &&
                Objects.equals(orderLineItems, that.orderLineItems) &&
                Objects.equals(deliveryAddress, that.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetAggregateIdentifier, restaurantId, orderLineItems, deliveryAddress);
    }
}
