package com.fraktalio.order.command.api;

public abstract class AbstractOrderEvent {

    private final AuditEntry auditEntry;

    public AbstractOrderEvent(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    public AuditEntry getAuditEntry() {
        return auditEntry;
    }
}
