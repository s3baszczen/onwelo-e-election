package com.onwelo.election.election.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ElectionName {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 200;

    @Column(nullable = false, length = MAX_LENGTH)
    private String value;

    public ElectionName(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        Objects.requireNonNull(value, "Election name cannot be null");
        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Election name cannot be empty");
        }

        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Election name must be at least %d characters long", MIN_LENGTH)
            );
        }

        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Election name must not exceed %d characters", MAX_LENGTH)
            );
        }

        return trimmed;
    }

    @Override
    public String toString() {
        return value;
    }
}
