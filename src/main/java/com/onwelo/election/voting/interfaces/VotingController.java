package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.application.VotingService;
import com.onwelo.election.voting.domain.model.Vote;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VotingController {

    private final VotingService votingService;
    private final VoteMapper voteMapper;

    @PostMapping
    public ResponseEntity<VoteResponse> vote(@RequestBody @Valid VoteRequest request) {
        Vote vote = votingService.vote(
                request.voterId(),
                request.electionId(),
                request.candidateId()
        );
        VoteResponse response = voteMapper.toResponse(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
