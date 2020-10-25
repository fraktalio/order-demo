package com.fraktalio.api;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class AuditEntry {

    private final String who;
    private final Date when;
    private final Collection<String> auth;

    public AuditEntry(String who, Date when, Collection<String> auth) {
        this.who = who;
        this.when = when;
        this.auth = auth;
    }

    public String getWho() {
        return who;
    }

    public Date getWhen() {
        return when;
    }

    public Collection<String> getAuth() {
        return auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuditEntry that = (AuditEntry) o;
        return Objects.equals(who, that.who) &&
                Objects.equals(when, that.when);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, when);
    }
}
