package com.onwelo.election.voting.infrastructure;

import com.onwelo.election.voting.domain.VoterRepository;
import com.onwelo.election.voting.domain.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoterJpaRepository extends JpaRepository<Voter, UUID>, VoterRepository {

}
