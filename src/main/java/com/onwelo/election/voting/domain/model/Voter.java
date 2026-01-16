package com.onwelo.election.voting.domain.model;

import com.onwelo.election.voting.domain.VoterBlockedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "external_user_id"))
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID externalUserId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoterStatus status;

    public Voter(UUID externalUserId, VoterStatus status) {
        this.externalUserId = Objects.requireNonNull(externalUserId, "externalUserId cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public void block() {
        this.status = VoterStatus.BLOCKED;
    }

    public void activate() {
        this.status = VoterStatus.ACTIVE;
    }

    public Vote vote(UUID electionId, UUID candidateId) {
        if (this.status == VoterStatus.BLOCKED) {
            throw new VoterBlockedException(this.id);
        }
        return new Vote(this.id, electionId, candidateId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(id, voter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
