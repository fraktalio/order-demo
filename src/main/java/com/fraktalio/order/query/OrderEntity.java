package com.fraktalio.order.query;

import com.fraktalio.order.query.api.OrderStatus;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

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

    OrderEntity(String id, String restaurantId,
                List<OrderLineItemEntity> orderLineItems, String deliveryAddress) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderState = OrderStatus.PLACED;
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
}
