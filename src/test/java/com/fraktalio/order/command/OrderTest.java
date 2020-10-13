package com.fraktalio.order.command;

import com.fraktalio.order.adapter.configuration.SpringSecurityReactorMessageDispatchInterceptor;
import com.fraktalio.order.command.api.AcceptOrderCommand;
import com.fraktalio.order.command.api.AuditEntry;
import com.fraktalio.order.command.api.MarkOrderAsCollectedCommand;
import com.fraktalio.order.command.api.MarkOrderAsDeliveredCommand;
import com.fraktalio.order.command.api.MarkOrderAsPayedCommand;
import com.fraktalio.order.command.api.MarkOrderAsPreparedCommand;
import com.fraktalio.order.command.api.OrderAcceptedEvent;
import com.fraktalio.order.command.api.OrderCollectedEvent;
import com.fraktalio.order.command.api.OrderDeliveredEvent;
import com.fraktalio.order.command.api.OrderId;
import com.fraktalio.order.command.api.OrderLineItem;
import com.fraktalio.order.command.api.OrderPayedEvent;
import com.fraktalio.order.command.api.OrderPlacedEvent;
import com.fraktalio.order.command.api.OrderPreparedEvent;
import com.fraktalio.order.command.api.OrderRejectedEvent;
import com.fraktalio.order.command.api.PlaceOrderCommand;
import com.fraktalio.order.command.api.RejectOrderCommand;
import com.fraktalio.order.command.api.RestaurantId;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.*;

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
                                                    "address");

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
                                                    "address");
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
                                                    "address");
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
                                                    "address");
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
                                                    "address");
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
                                                    "address");
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
                                                    "address");
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
