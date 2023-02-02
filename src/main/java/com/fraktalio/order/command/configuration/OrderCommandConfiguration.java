package com.fraktalio.order.command.configuration;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolution;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolver;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class OrderCommandConfiguration {

    /************************************************/
    /*       Register interceptors on the bus       */

    /************************************************/

    @Autowired
    public void registerCommandInterceptorsOnBus(CommandBus commandBus) {
        commandBus.registerHandlerInterceptor(new LoggingInterceptor<>());
    }

    @Autowired
    public void registerEventInterceptors(EventBus eventBus) {
        eventBus.registerDispatchInterceptor(new LoggingInterceptor<>());
    }

    @Autowired
    public void configure(EventProcessingConfigurer config) {
        config.registerDefaultHandlerInterceptor((t, u) -> new LoggingInterceptor<>(u));
    }

    /***************************************/
    /*    Aggregate cache configuration    */

    /***************************************/

    @Bean("cache")
    public Cache cache() {
        return new WeakReferenceCache();
    }

    /***************************************/
    /*  Aggregate snapshot configuration   */

    /***************************************/

    @Bean
    public SpringAggregateSnapshotterFactoryBean snapshotter() {
        var springAggregateSnapshotterFactoryBean = new SpringAggregateSnapshotterFactoryBean();
        //Setting async executors
        springAggregateSnapshotterFactoryBean.setExecutor(Executors.newSingleThreadExecutor());
        return springAggregateSnapshotterFactoryBean;
    }

    @Bean("orderSnapshotTriggerDefinition")
    EventCountSnapshotTriggerDefinition orderSnapshotTriggerDefinition(Snapshotter snapshotter,
                                                                       OrderProperties orderProperties) {
        return new EventCountSnapshotTriggerDefinition(snapshotter,
                orderProperties.getSnapshotTriggerThresholdOrder());
    }

    /***************************************************************************/
    /* Duplicate command handler configured to fail on duplicate registrations */

    /* Command is always routed to a single destination.
    This means that during the registration of a command handler within a given JVM,
    a second registration of an identical command handler method should be dealt with in a desirable manner.

    By default, the LoggingDuplicateCommandHandlerResolver is used, which will log a warning and returns the candidate handler.*/

    /***************************************************************************/

    @Bean
    public DuplicateCommandHandlerResolver duplicateCommandHandlerResolver() {
        return DuplicateCommandHandlerResolution.rejectDuplicates();
    }
}
