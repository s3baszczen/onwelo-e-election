package com.onwelo.election.voting.infrastructure;

import com.onwelo.election.voting.domain.VoteRepository;
import com.onwelo.election.voting.domain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteJpaRepository extends JpaRepository<Vote, UUID>, VoteRepository {

}
