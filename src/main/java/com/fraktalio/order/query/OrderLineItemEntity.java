package com.fraktalio.order.query;


import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
class OrderLineItemEntity {

    private String menuItemId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    protected OrderLineItemEntity() {
    }

    OrderLineItemEntity(String menuItemId, String name, BigDecimal price, Integer quantity) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    String getMenuItemId() {
        return menuItemId;
    }

    void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    BigDecimal getPrice() {
        return price;
    }

    void setPrice(BigDecimal price) {
        this.price = price;
    }

    Integer getQuantity() {
        return quantity;
    }

    void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderLineItemEntity that = (OrderLineItemEntity) o;
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
