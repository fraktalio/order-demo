package com.fraktalio.restaurant.query.api;

import java.util.List;
import java.util.Objects;

public class MenuModel {

    private final String id;
    private final String menuVersion;
    private final String restaurantId;
    private final Boolean active;
    private final List<MenuItemModel> menuItems;

    public MenuModel(String id, String menuVersion, String restaurantId, Boolean active,
                     List<MenuItemModel> menuItems) {
        this.id = id;
        this.menuVersion = menuVersion;
        this.restaurantId = restaurantId;
        this.active = active;
        this.menuItems = menuItems;
    }

    public String getId() {
        return id;
    }

    public String getMenuVersion() {
        return menuVersion;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Boolean getActive() {
        return active;
    }

    public List<MenuItemModel> getMenuItems() {
        return menuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuModel menuModel = (MenuModel) o;
        return Objects.equals(id, menuModel.id) &&
                Objects.equals(menuVersion, menuModel.menuVersion) &&
                Objects.equals(restaurantId, menuModel.restaurantId) &&
                Objects.equals(active, menuModel.active) &&
                Objects.equals(menuItems, menuModel.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuVersion, restaurantId, active, menuItems);
    }
}
