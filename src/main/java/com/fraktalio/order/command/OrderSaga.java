package com.fraktalio.order.command;

import com.fraktalio.courier.command.api.*;
import com.fraktalio.order.command.api.*;
import com.fraktalio.restaurant.command.api.RestaurantId;
import com.fraktalio.restaurant.command.api.*;
import jakarta.persistence.Transient;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Saga
@ProcessingGroup("order-saga")
public class OrderSaga {

    public OrderSaga() {
    }

    @Autowired
    @Transient
    private ReactorCommandGateway commandGateway;

    private OrderId orderId;
    private RestaurantOrderId restaurantOrderId;
    private ShipmentId shipmentId;

    public OrderId getOrderId() {
        return orderId;
    }

    public RestaurantOrderId getRestaurantOrderId() {
        return restaurantOrderId;
    }

    public ShipmentId getShipmentId() {
        return shipmentId;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "aggregateIdentifier")
    void on(OrderPlacedEvent event) {
        this.orderId = event.getAggregateIdentifier();
        this.restaurantOrderId = new RestaurantOrderId();
        SagaLifecycle.associateWith("restaurantOrderId", restaurantOrderId.toString());

        var restaurantOrderLineItems = event.getOrderLineItems().stream()
                .map(oli -> new RestaurantOrderLineItem(oli.getQuantity(),
                        oli.getMenuItemId(),
                        oli.getName())).collect(Collectors
                        .toList());
        var placeRestaurantOrderCommand = new PlaceRestaurantOrderCommand(new RestaurantId(event.getRestaurantId()
                .getIdentifier()),
                new RestaurantOrderDetails(
                        restaurantOrderLineItems),
                restaurantOrderId);
        commandGateway.send(placeRestaurantOrderCommand).block();
    }

    @SagaEventHandler(associationProperty = "restaurantOrderId")
    void on(RestaurantOrderPlacedEvent event) {
        var acceptOrderCommand = new AcceptOrderCommand(orderId);
        commandGateway.send(acceptOrderCommand).block();
    }

    @SagaEventHandler(keyName = "restaurantOrderId", associationProperty = "aggregateIdentifier")
    void on(RestaurantOrderPreparedEvent event) {
        var markOrderAsPreparedCommand = new MarkOrderAsPreparedCommand(orderId);
        commandGateway.send(markOrderAsPreparedCommand).block();
    }

    @EndSaga
    @SagaEventHandler(keyName = "restaurantOrderId", associationProperty = "aggregateIdentifier")
    void on(RestaurantOrderRejectedEvent event) {
        var rejectOrderCommand = new RejectOrderCommand(orderId);
        commandGateway.send(rejectOrderCommand).block();
    }

    @SagaEventHandler(associationProperty = "aggregateIdentifier")
    void on(OrderPreparedEvent event) {
        this.shipmentId = new ShipmentId();
        SagaLifecycle.associateWith("shipmentId", shipmentId.toString());
        var createShipmentCommand = new CreateShipmentCommand(shipmentId, new Address(event.getDeliveryAddress(),
                event.getDeliveryAddress()));
        commandGateway.send(createShipmentCommand).block();
    }

    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentAssignedEvent event) {
        var markOrderAsCollectedCommand = new MarkOrderAsCollectedCommand(orderId);
        commandGateway.send(markOrderAsCollectedCommand).block();
    }

    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentExpiredEvent event) {
        var markOrderAsExpiredCommand = new MarkOrderAsExpiredCommand(orderId);
        commandGateway.send(markOrderAsExpiredCommand).block();
    }

    @EndSaga
    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentDeliveredEvent event) {
        var markOrderAsDeliveredCommand = new MarkOrderAsDeliveredCommand(orderId);
        commandGateway.send(markOrderAsDeliveredCommand).block();
    }
}
