package com.fraktalio.order.query;

import com.fraktalio.api.AuditEntry;
import com.fraktalio.order.command.api.*;
import com.fraktalio.order.query.api.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Tracking event processor - Eventual consistency
 */
@Component
@ProcessingGroup("OrderProcessor")
class OrderHandler {

    private final OrderRepository orderRepository;
    private final QueryUpdateEmitter queryUpdateEmitter;

    private OrderModel convert(OrderEntity entity) {
        return new OrderModel(entity.getId(),
                entity.getRestaurantId(),
                entity.getOrderLineItems().stream().map(oli -> new OrderLineItemModel(oli.getMenuItemId(),
                                oli.getName(),
                                oli.getPrice(),
                                oli.getQuantity()))
                        .collect(Collectors.toList()),
                entity.getDeliveryAddress(),
                entity.getOrderState());
    }


    OrderHandler(OrderRepository orderRepository, QueryUpdateEmitter queryUpdateEmitter) {
        this.orderRepository = orderRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    void on(OrderPlacedEvent event) {
        var record = orderRepository.save(new OrderEntity(event.getAggregateIdentifier().getIdentifier(),
                event.getRestaurantId().getIdentifier(),
                event.getOrderLineItems().stream()
                        .map(oli -> new OrderLineItemEntity(oli.getMenuItemId(),
                                oli.getName(),
                                oli.getPrice(),
                                oli.getQuantity()))
                        .collect(Collectors.toList()),
                event.getDeliveryAddress(),
                event.getUserId()));

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderAcceptedEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.ACCEPTED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderPreparedEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.PREPARED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderCollectedEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.PREPARED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderExpiredEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.EXPIRED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderDeliveredEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.DELIVERED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @EventHandler
    void on(OrderPayedEvent event) {
        var record = orderRepository.findById(event.getAggregateIdentifier().getIdentifier())
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Order with id '" + event.getAggregateIdentifier() + "' not found"));

        record.setOrderState(OrderStatus.PAYED);
        orderRepository.save(record);

        queryUpdateEmitter.emit(
                FindAllOrdersQuery.class,
                filter -> true,
                convert(record));

        queryUpdateEmitter.emit(
                FindAllOrdersByUserIdQuery.class,
                query -> query.getUserId().equals(record.getUserId()),
                convert(record));
    }

    @QueryHandler
    List<OrderModel> on(FindAllOrdersQuery query, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        return orderRepository.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @QueryHandler
    List<OrderModel> on(FindAllOrdersByUserIdQuery query, @MetaDataValue(value = "auditEntry") AuditEntry auditEntry) {
        return orderRepository.findAllByUserId(query.getUserId()).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
