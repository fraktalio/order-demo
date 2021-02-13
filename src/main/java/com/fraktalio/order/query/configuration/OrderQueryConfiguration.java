package com.fraktalio.order.query.configuration;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
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

    /*******************************************************************************************************************************/
    /* Register default listener invocation handler on the event processors */
    /* By default these exceptions are logged and processing continues with the next handler or message */
    /* We change this behaviour by configuring custom listener PropagatingErrorHandler to re-throw the exception to the processor. */

    /* https://docs.axoniq.io/reference-guide/axon-framework/events/event-processors#exceptions-raised-by-event-handler-methods */

    /*******************************************************************************************************************************/

    @Autowired
    public void configureErrorHandling(EventProcessingConfigurer config) {
        config.registerDefaultListenerInvocationErrorHandler(configuration -> PropagatingErrorHandler.instance());
    }
}
