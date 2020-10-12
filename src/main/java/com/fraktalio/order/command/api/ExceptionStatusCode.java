package com.fraktalio.order.command.api;

public enum ExceptionStatusCode {

    ORDER_NOT_PLACED("The order is not in PLACED state."),
    ORDER_NOT_ACCEPTED("Shipment is not in ACCEPTED state."),
    ORDER_NOT_PREPARED("The order is not in PREPARED state."),
    ORDER_NOT_COLLECTED("The order is not in COLLECTED state."),
    ORDER_NOT_DELIVERED("The order is not in DELIVERED state.");

    private final String description;

    ExceptionStatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
