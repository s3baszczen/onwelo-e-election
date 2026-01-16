package com.onwelo.election.election.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Candiate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    private Election election;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candiate candiate = (Candiate) o;
        return Objects.equals(id, candiate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
