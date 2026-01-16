package com.onwelo.election.election.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    public Candidate(String name) {
        this.name = Objects.requireNonNull(name, "Candidate name cannot be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Candidate name cannot be empty");
        }
    }

    void setElection(Election election) {
        this.election = election;
    }
}

