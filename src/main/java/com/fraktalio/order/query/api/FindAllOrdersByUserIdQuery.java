package com.fraktalio.order.query.api;

import java.util.Objects;

public final class FindAllOrdersByUserIdQuery {

    private final String userId;

    public FindAllOrdersByUserIdQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FindAllOrdersByUserIdQuery that = (FindAllOrdersByUserIdQuery) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
