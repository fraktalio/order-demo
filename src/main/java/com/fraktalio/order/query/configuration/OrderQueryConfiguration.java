package com.fraktalio.order.query.configuration;

import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderQueryConfiguration {

    /************************************************/
    /* Register interceptors on the bus */

    /************************************************/

    @Autowired
    public void registerQueryInterceptorsOnBus(QueryBus queryBus) {
        queryBus.registerHandlerInterceptor(new LoggingInterceptor<>());
    }
}
