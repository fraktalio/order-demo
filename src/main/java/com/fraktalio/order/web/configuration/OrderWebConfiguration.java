package com.fraktalio.order.web.configuration;

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderWebConfiguration {

    /***************************************************/
    /* Register a dispatch interceptors on the gateway */

    /***************************************************/

    @Autowired
    void registerCommandInterceptorsOnReactiveGateway(ReactorCommandGateway reactorCommandGateway) {
        reactorCommandGateway.registerDispatchInterceptor(new SpringSecurityReactorMessageDispatchInterceptor());
        reactorCommandGateway.registerDispatchInterceptor(new LoggingReactorMessageDispatchInterceptor());
    }

    @Autowired
    void registerQueryInterceptorsOnReactiveGateway(ReactorQueryGateway reactorQueryGateway) {
        reactorQueryGateway.registerDispatchInterceptor(new SpringSecurityReactorMessageDispatchInterceptor());
        reactorQueryGateway.registerDispatchInterceptor(new LoggingReactorMessageDispatchInterceptor());
    }
}
