package com.fraktalio.order.web.configuration;


import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import org.axonframework.messaging.Message;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class LoggingReactorMessageDispatchInterceptor<M extends Message<?>>
        implements ReactorMessageDispatchInterceptor<M> {

    @Override
    public Mono<M> intercept(Mono<M> message) {
        return message.doOnNext(m -> {
            LoggerFactory.getLogger(LoggingReactorMessageDispatchInterceptor.class).info(
                    "Dispatched message " + m.getPayload().getClass() + "with metadata " + m.getMetaData());
        });
    }
}
