package com.fraktalio.order.command.api;

import com.fraktalio.api.AuditEntry;

public abstract class AbstractOrderEvent {

    private final AuditEntry auditEntry;

    public AbstractOrderEvent(AuditEntry auditEntry) {
        this.auditEntry = auditEntry;
    }

    public AuditEntry getAuditEntry() {
        return auditEntry;
    }
}
