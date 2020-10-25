package com.fraktalio.order.command;

import com.fraktalio.courier.command.api.Address;
import com.fraktalio.courier.command.api.CreateShipmentCommand;
import com.fraktalio.courier.command.api.ShipmentAssignedEvent;
import com.fraktalio.courier.command.api.ShipmentDeliveredEvent;
import com.fraktalio.courier.command.api.ShipmentExpiredEvent;
import com.fraktalio.courier.command.api.ShipmentId;
import com.fraktalio.order.command.api.AcceptOrderCommand;
import com.fraktalio.order.command.api.MarkOrderAsCollectedCommand;
import com.fraktalio.order.command.api.MarkOrderAsDeliveredCommand;
import com.fraktalio.order.command.api.MarkOrderAsExpiredCommand;
import com.fraktalio.order.command.api.MarkOrderAsPreparedCommand;
import com.fraktalio.order.command.api.OrderId;
import com.fraktalio.order.command.api.OrderPlacedEvent;
import com.fraktalio.order.command.api.OrderPreparedEvent;
import com.fraktalio.order.command.api.RejectOrderCommand;
import com.fraktalio.restaurant.command.api.PlaceRestaurantOrderCommand;
import com.fraktalio.restaurant.command.api.RestaurantId;
import com.fraktalio.restaurant.command.api.RestaurantOrderDetails;
import com.fraktalio.restaurant.command.api.RestaurantOrderId;
import com.fraktalio.restaurant.command.api.RestaurantOrderLineItem;
import com.fraktalio.restaurant.command.api.RestaurantOrderPlacedEvent;
import com.fraktalio.restaurant.command.api.RestaurantOrderPreparedEvent;
import com.fraktalio.restaurant.command.api.RestaurantOrderRejectedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import javax.persistence.Transient;

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
        commandGateway.send(placeRestaurantOrderCommand).subscribe();
    }

    @SagaEventHandler(associationProperty = "restaurantOrderId")
    void on(RestaurantOrderPlacedEvent event) {
        var acceptOrderCommand = new AcceptOrderCommand(orderId);
        commandGateway.send(acceptOrderCommand).subscribe();
    }

    @SagaEventHandler(keyName = "restaurantOrderId", associationProperty = "aggregateIdentifier")
    void on(RestaurantOrderPreparedEvent event) {
        var markOrderAsPreparedCommand = new MarkOrderAsPreparedCommand(orderId);
        commandGateway.send(markOrderAsPreparedCommand).subscribe();
    }

    @EndSaga
    @SagaEventHandler(keyName = "restaurantOrderId", associationProperty = "aggregateIdentifier")
    void on(RestaurantOrderRejectedEvent event) {
        var rejectOrderCommand = new RejectOrderCommand(orderId);
        commandGateway.send(rejectOrderCommand).subscribe();
    }

    @SagaEventHandler(associationProperty = "aggregateIdentifier")
    void on(OrderPreparedEvent event) {
        this.shipmentId = new ShipmentId();
        SagaLifecycle.associateWith("shipmentId", shipmentId.toString());
        var createShipmentCommand = new CreateShipmentCommand(shipmentId, new Address(event.getDeliveryAddress(),
                                                                                      event.getDeliveryAddress()));
        commandGateway.send(createShipmentCommand).subscribe();
    }

    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentAssignedEvent event) {
        var markOrderAsCollectedCommand = new MarkOrderAsCollectedCommand(orderId);
        commandGateway.send(markOrderAsCollectedCommand).subscribe();
    }

    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentExpiredEvent event) {
        var markOrderAsExpiredCommand = new MarkOrderAsExpiredCommand(orderId);
        commandGateway.send(markOrderAsExpiredCommand).subscribe();
    }

    @EndSaga
    @SagaEventHandler(keyName = "shipmentId", associationProperty = "aggregateIdentifier")
    void on(ShipmentDeliveredEvent event) {
        var markOrderAsDeliveredCommand = new MarkOrderAsDeliveredCommand(orderId);
        commandGateway.send(markOrderAsDeliveredCommand).subscribe();
    }
}
