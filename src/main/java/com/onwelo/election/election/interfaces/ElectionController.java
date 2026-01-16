package com.onwelo.election.election.interfaces;

import com.onwelo.election.election.application.ElectionService;
import com.onwelo.election.election.domain.model.Candidate;
import com.onwelo.election.election.domain.model.Election;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/elections")
public class ElectionController {

    private final ElectionMapper electionMapper;
    private final ElectionService electionService;

    @PostMapping
    public ResponseEntity<ElectionResponse> registerElection(@RequestBody @Valid RegisterElectionRequest electionRequest) {
        Set<Candidate> candidates = electionMapper.candidateRequestsToDomain(electionRequest.candidates());
        Election election = electionMapper.toDomain(electionRequest, candidates);
        Election registeredElection = electionService.registerElection(election);
        ElectionResponse response = electionMapper.toResponse(registeredElection);
        return ResponseEntity.created(URI.create("/" + registeredElection.getId()))
                .body(response);
    }

    @PatchMapping("/{electionId}/candidates")
    public ResponseEntity<ElectionResponse> updateElectionCandidates(
            @PathVariable UUID electionId,
            @RequestBody @Valid UpdateElectionCandidatesRequest request) {
        Set<Candidate> candidates = electionMapper.candidateRequestsToDomain(request.candidates());
        Election updatedElection = electionService.updateElectionCandidates(electionId, candidates);
        ElectionResponse response = electionMapper.toResponse(updatedElection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ElectionResponse> getElection(@PathVariable UUID electionId) {
        Election election = electionService.getElection(electionId);
        ElectionResponse response = electionMapper.toResponse(election);
        return ResponseEntity.ok(response);
    }
}
