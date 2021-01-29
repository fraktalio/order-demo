package com.fraktalio.order.web.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.ExponentialBackOffIntervalRetryScheduler;
import org.axonframework.extensions.reactor.commandhandling.gateway.DefaultReactorCommandGateway;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

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

    /***************************************************/
    /* The RetryScheduler is capable of scheduling retries when command execution has failed.
    When a command fails due to an exception that is explicitly non-transient, no retries are done at all.
    Note that the retry scheduler is only invoked when a command fails due to a RuntimeException.
    Checked exceptions are regarded as a "business exception" and will never trigger a retry. */
    /***************************************************/
    @Bean
    public ReactorCommandGateway reactiveCommandGateway(CommandBus commandBus) {
        var scheduledExecutorService = Executors.newScheduledThreadPool(5);
        var retryScheduler = ExponentialBackOffIntervalRetryScheduler
                .builder()
                .retryExecutor(scheduledExecutorService)
                .maxRetryCount(3)
                .backoffFactor(1000)
                .build();

        return DefaultReactorCommandGateway.builder()
                                           .commandBus(commandBus)
                                           .retryScheduler(retryScheduler)
                                           .build();
    }

    //TODO configure retry scheduler for query gateways: https://github.com/AxonFramework/AxonFramework/issues/1692

}
