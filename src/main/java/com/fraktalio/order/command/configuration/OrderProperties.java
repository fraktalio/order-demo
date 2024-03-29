package com.fraktalio.order.command.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order")
public class OrderProperties {

    public OrderProperties(Integer snapshotTriggerThresholdOrder) {
        this.snapshotTriggerThresholdOrder = snapshotTriggerThresholdOrder;
    }

    public OrderProperties() {
    }

    private Integer snapshotTriggerThresholdOrder = 100;

    public Integer getSnapshotTriggerThresholdOrder() {
        return snapshotTriggerThresholdOrder;
    }


    public void setSnapshotTriggerThresholdOrder(Integer snapshotTriggerThresholdOrder) {
        this.snapshotTriggerThresholdOrder = snapshotTriggerThresholdOrder;
    }
}



