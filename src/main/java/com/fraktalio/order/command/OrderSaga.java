package com.fraktalio.order.command;

import com.fraktalio.order.command.api.OrderPlacedEvent;
import com.fraktalio.restaurant.command.api.PlaceRestaurantOrderCommand;
import com.fraktalio.restaurant.command.api.RestaurantId;
import com.fraktalio.restaurant.command.api.RestaurantOrderDetails;
import com.fraktalio.restaurant.command.api.RestaurantOrderId;
import com.fraktalio.restaurant.command.api.RestaurantOrderLineItem;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
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

    @Autowired
    @Transient
    private CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "aggregateIdentifier")
    void on(OrderPlacedEvent event) {
        var restaurantOrderId = new RestaurantOrderId();
        SagaLifecycle.associateWith("restaurantOrderId", restaurantOrderId.toString());
        var restaurantOrderLineItems = event.getOrderLineItems().stream()
                                            .map(oli -> new RestaurantOrderLineItem(oli.getQuantity(),
                                                                                    oli.getMenuItemId(),
                                                                                    oli.getName())).collect(Collectors.toList());
        var placeRestaurantOrderCommand = new PlaceRestaurantOrderCommand(new RestaurantId(event.getRestaurantId().getIdentifier()),
                                                                          new RestaurantOrderDetails(restaurantOrderLineItems),
                                                                          restaurantOrderId);
        commandGateway.send(placeRestaurantOrderCommand);
    }
}
