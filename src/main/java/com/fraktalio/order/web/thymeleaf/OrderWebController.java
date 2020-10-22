package com.fraktalio.order.web.thymeleaf;

import com.fraktalio.order.command.api.OrderId;
import com.fraktalio.order.command.api.PlaceOrderCommand;
import com.fraktalio.order.command.api.RestaurantId;
import com.fraktalio.order.query.api.FindAllOrdersByUserIdQuery;
import com.fraktalio.order.query.api.OrderModel;
import com.fraktalio.order.web.api.PlaceOrderRequest;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
public class OrderWebController {

    private final ReactorCommandGateway reactorCommandGateway;
    private final ReactorQueryGateway reactorQueryGateway;

    public OrderWebController(
            ReactorCommandGateway reactorCommandGateway,
            ReactorQueryGateway reactorQueryGateway) {
        this.reactorCommandGateway = reactorCommandGateway;
        this.reactorQueryGateway = reactorQueryGateway;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/customer-orders-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Mono<String> ordersSSE(Model model,  @AuthenticationPrincipal UserDetails user) {
        Flux<OrderModel> result =
                reactorQueryGateway.subscriptionQueryMany(new FindAllOrdersByUserIdQuery(user.getUsername()), OrderModel.class);
        model.addAttribute("orders", new ReactiveDataDriverContextVariable(result, 1));
        return Mono.just("sse/customer-orders-sse");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer-orders")
    Mono<String> orders(Model model) {
        model.addAttribute("placeOrderRequest", new PlaceOrderRequest());
        return Mono.just("customer-orders");
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/customer-orders")
    Mono<String> placeOrder(@Valid @ModelAttribute PlaceOrderRequest placeOrderRequest,
                            BindingResult bindingResult,
                            Model model
    ) {

        var command = new PlaceOrderCommand(new OrderId(),
                                            new RestaurantId(placeOrderRequest.getRestaurantId()),
                                            placeOrderRequest.getOrderLineItems(),
                                            placeOrderRequest.getDeliveryAddress());
        Mono<OrderId> result = reactorCommandGateway.send(command);

        return Mono
                .just(bindingResult)
                .map(Errors::hasErrors)
                .filter(aBoolean -> aBoolean == true)
                .flatMap(aBoolean -> Mono.just("customer-orders"))
                .switchIfEmpty(result.thenReturn("redirect:/customer-orders"));
    }
}
