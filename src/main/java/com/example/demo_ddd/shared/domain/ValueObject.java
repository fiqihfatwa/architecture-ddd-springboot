package com.example.demo_ddd.shared.domain;

import java.util.Objects;

public abstract class ValueObject {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.toString(), obj.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
