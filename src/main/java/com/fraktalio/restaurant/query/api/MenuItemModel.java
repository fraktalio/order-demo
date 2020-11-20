package com.fraktalio.restaurant.query.api;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuItemModel {
    private final String menuId;
    private final String name;
    private final BigDecimal price;

    public MenuItemModel(String menuId, String name, BigDecimal price) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItemModel that = (MenuItemModel) o;
        return Objects.equals(menuId, that.menuId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, name, price);
    }
}
