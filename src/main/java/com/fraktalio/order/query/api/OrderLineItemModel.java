package com.fraktalio.order.query.api;


import java.math.BigDecimal;
import java.util.Objects;

public final class OrderLineItemModel {

    private final String menuItemId;
    private final String name;
    private final BigDecimal price;
    private final Integer quantity;

    public OrderLineItemModel(String menuItemId, String name, BigDecimal price, Integer quantity) {
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

    public BigDecimal getPrice() {
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
        OrderLineItemModel that = (OrderLineItemModel) o;
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
