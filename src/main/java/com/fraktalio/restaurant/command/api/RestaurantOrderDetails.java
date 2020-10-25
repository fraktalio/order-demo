package com.fraktalio.restaurant.command.api;

import java.util.List;

public class RestaurantOrderDetails {

    private final List<RestaurantOrderLineItem> lineItems;

    public RestaurantOrderDetails(List<RestaurantOrderLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<RestaurantOrderLineItem> getLineItems() {
        return lineItems;
    }
}
