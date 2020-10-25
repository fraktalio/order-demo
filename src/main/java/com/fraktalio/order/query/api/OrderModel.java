package com.fraktalio.order.query.api;

import java.util.List;

public class OrderModel {

    private final String id;
    private final String restaurantId;
    private final List<OrderLineItemModel> orderLineItems;
    private final String deliveryAddress;
    private final OrderStatus orderState;

    public OrderModel(String id, String restaurantId,
                      List<OrderLineItemModel> orderLineItems, String deliveryAddress, OrderStatus orderState) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderState = orderState;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<OrderLineItemModel> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderStatus getOrderState() {
        return orderState;
    }
}
