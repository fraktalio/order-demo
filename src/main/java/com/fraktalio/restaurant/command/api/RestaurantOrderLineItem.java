package com.fraktalio.restaurant.command.api;

import java.util.Objects;

public class RestaurantOrderLineItem {
    private final Integer quantity;
    private final String menuItemId;
    private final String name;

    public RestaurantOrderLineItem(Integer quantity, String menuItemId, String name) {
        this.quantity = quantity;
        this.menuItemId = menuItemId;
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantOrderLineItem that = (RestaurantOrderLineItem) o;
        return Objects.equals(quantity, that.quantity) &&
                Objects.equals(menuItemId, that.menuItemId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, menuItemId, name);
    }
}
