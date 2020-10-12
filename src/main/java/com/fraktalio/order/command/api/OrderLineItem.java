package com.fraktalio.order.command.api;


import com.google.type.Money;

import java.util.Objects;

public final class OrderLineItem {

    private final String menuItemId;
    private final String name;
    private final Money price;
    private final Integer quantity;

    public OrderLineItem(String menuItemId, String name, Money price, Integer quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderLineItem that = (OrderLineItem) o;
        return Objects.equals(menuItemId, that.menuItemId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemId, name, price, quantity);
    }
}
