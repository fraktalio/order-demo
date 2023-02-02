package com.fraktalio.order.command;

import com.fraktalio.api.AuditEntry;
import com.fraktalio.order.command.api.*;
import com.fraktalio.order.web.configuration.SpringSecurityReactorMessageDispatchInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;

public class OrderTest {

    private AggregateTestFixture<Order> testFixture;
    private OrderId targetAggregateIdentifier;
    private RestaurantId restaurantId;
    private AuditEntry auditEntry;

    @BeforeEach
    void setUp() {
        testFixture = new AggregateTestFixture<>(Order.class);
        testFixture.registerCommandDispatchInterceptor(new SpringSecurityReactorMessageDispatchInterceptor<>());
        targetAggregateIdentifier = new OrderId();
        restaurantId = new RestaurantId();
        auditEntry = new AuditEntry("anonymous",
                Calendar.getInstance()
                        .getTime(),
                Collections.singletonList("anonymous"));
    }

    @Test
    void placeOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var placeOrderCommand = new PlaceOrderCommand(targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");
        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");

        testFixture.given()
                .when(placeOrderCommand)
                .expectEvents(orderPlacedEvent);
    }

    @Test
    void acceptOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var acceptOrderCommand = new AcceptOrderCommand(targetAggregateIdentifier);
        var orderAcceptedEvent = new OrderAcceptedEvent(auditEntry, targetAggregateIdentifier);

        testFixture.given(orderPlacedEvent)
                .when(acceptOrderCommand)
                .expectEvents(orderAcceptedEvent);
    }

    @Test
    void rejectOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var rejectOrderCommand = new RejectOrderCommand(targetAggregateIdentifier);
        var orderRejectedEvent = new OrderRejectedEvent(auditEntry, targetAggregateIdentifier);

        testFixture.given(orderPlacedEvent)
                .when(rejectOrderCommand)
                .expectEvents(orderRejectedEvent);
    }

    @Test
    void preparedOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var orderAcceptedEvent = new OrderAcceptedEvent(auditEntry, targetAggregateIdentifier);
        var markOrderAsPreparedCommand = new MarkOrderAsPreparedCommand(targetAggregateIdentifier);
        var orderPreparedEvent = new OrderPreparedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");


        testFixture.given(orderPlacedEvent, orderAcceptedEvent)
                .when(markOrderAsPreparedCommand)
                .expectEvents(orderPreparedEvent);
    }

    @Test
    void collectedOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var orderAcceptedEvent = new OrderAcceptedEvent(auditEntry, targetAggregateIdentifier);
        var orderPreparedEvent = new OrderPreparedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");
        var markOrderAsCollectedCommand = new MarkOrderAsCollectedCommand(targetAggregateIdentifier);
        var orderCollectedEvent = new OrderCollectedEvent(auditEntry, targetAggregateIdentifier);


        testFixture.given(orderPlacedEvent, orderAcceptedEvent, orderPreparedEvent)
                .when(markOrderAsCollectedCommand)
                .expectEvents(orderCollectedEvent);
    }

    @Test
    void deliveredOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var orderAcceptedEvent = new OrderAcceptedEvent(auditEntry, targetAggregateIdentifier);
        var orderPreparedEvent = new OrderPreparedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");
        var orderCollectedEvent = new OrderCollectedEvent(auditEntry, targetAggregateIdentifier);

        var markOrderAsDeliveredCommand = new MarkOrderAsDeliveredCommand(targetAggregateIdentifier);
        var orderDeliveredEvent = new OrderDeliveredEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");


        testFixture.given(orderPlacedEvent, orderAcceptedEvent, orderPreparedEvent, orderCollectedEvent)
                .when(markOrderAsDeliveredCommand)
                .expectEvents(orderDeliveredEvent);
    }

    @Test
    void payedOrderTest() {
        OrderLineItem item1 = new OrderLineItem("1", "name1", BigDecimal.TEN, 22);

        var orderPlacedEvent = new OrderPlacedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address",
                "anonymous");
        var orderAcceptedEvent = new OrderAcceptedEvent(auditEntry, targetAggregateIdentifier);
        var orderPreparedEvent = new OrderPreparedEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");
        var orderCollectedEvent = new OrderCollectedEvent(auditEntry, targetAggregateIdentifier);
        var orderDeliveredEvent = new OrderDeliveredEvent(auditEntry, targetAggregateIdentifier,
                restaurantId,
                Collections.singletonList(item1),
                "address");

        var markOrderAsPayedCommand = new MarkOrderAsPayedCommand(targetAggregateIdentifier);
        var orderPayedEvent = new OrderPayedEvent(auditEntry, targetAggregateIdentifier);


        testFixture.given(orderPlacedEvent,
                        orderAcceptedEvent,
                        orderPreparedEvent,
                        orderCollectedEvent,
                        orderDeliveredEvent)
                .when(markOrderAsPayedCommand)
                .expectEvents(orderPayedEvent);
    }
}
