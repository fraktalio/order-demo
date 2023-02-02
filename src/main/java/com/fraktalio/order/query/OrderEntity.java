package com.fraktalio.order.query;

import com.fraktalio.order.query.api.OrderStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
class OrderEntity {

    @Id
    private String id;
    private String restaurantId;
    @ElementCollection
    private List<OrderLineItemEntity> orderLineItems;
    private String deliveryAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderState;
    private String userId;

    OrderEntity(String id, String restaurantId,
                List<OrderLineItemEntity> orderLineItems, String deliveryAddress, String userId) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderState = OrderStatus.PLACED;
        this.userId = userId;
    }

    protected OrderEntity() {
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getRestaurantId() {
        return restaurantId;
    }

    void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    List<OrderLineItemEntity> getOrderLineItems() {
        return orderLineItems;
    }

    void setOrderLineItems(List<OrderLineItemEntity> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    String getDeliveryAddress() {
        return deliveryAddress;
    }

    void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    OrderStatus getOrderState() {
        return orderState;
    }

    void setOrderState(OrderStatus orderState) {
        this.orderState = orderState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
