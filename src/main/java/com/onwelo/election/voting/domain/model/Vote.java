package com.onwelo.election.voting.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"voter_id", "election_id"}))
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "voter_id", nullable = false)
    private UUID voterId;

    @Column(name = "election_id", nullable = false)
    private UUID electionId;

    @Column(name = "candidate_id", nullable = false)
    private UUID candidateId;

    @Column(nullable = false)
    private Instant createdAt;

    public Vote(UUID voterId, UUID electionId, UUID candidateId) {
        this.voterId = Objects.requireNonNull(voterId, "Voter ID cannot be null");
        this.electionId = Objects.requireNonNull(electionId, "Election ID cannot be null");
        this.candidateId = Objects.requireNonNull(candidateId, "Candidate ID cannot be null");
        this.createdAt = Instant.now();
    }
}
