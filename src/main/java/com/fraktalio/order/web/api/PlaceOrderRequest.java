package com.fraktalio.order.web.api;

import com.fraktalio.order.command.api.OrderLineItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class PlaceOrderRequest {

    private String restaurantId;
    private List<OrderLineItem> orderLineItems;
    private String deliveryAddress;


    public PlaceOrderRequest(String restaurantId, List<OrderLineItem> orderLineItems, String deliveryAddress) {
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public PlaceOrderRequest() {
        orderLineItems = Collections.singletonList(new OrderLineItem("1", "Sarma", BigDecimal.TEN, 1));
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
