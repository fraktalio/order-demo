package com.fraktalio.order.web.configuration;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import org.axonframework.messaging.Message;
import reactor.core.publisher.Mono;

@Slf4j
public class LoggingReactorMessageDispatchInterceptor<M extends Message<?>>
        implements ReactorMessageDispatchInterceptor<M> {

    @Override
    public Mono<M> intercept(Mono<M> message) {
        return message.doOnNext(m -> {
            log.info("Dispatched message " + m.getPayload().getClass() + "with metadata " + m.getMetaData());
        });
    }
}
