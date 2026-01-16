package com.onwelo.election.election.interfaces;

import com.onwelo.election.voting.application.ElectionService;
import com.onwelo.election.voting.domain.model.Election;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/elections")
public class ElectionController {

    private final ElectionMapper electionMapper;
    private final ElectionService electionService;

    @PostMapping
    public ResponseEntity<ElectionResponse> registerANewElection(@RequestBody @Valid RegisterElectionRequest electionRequest) {
        Election user = electionMapper.toDomain(electionRequest);
        Election election = electionService.registerUserAsNewElection(user);
        ElectionResponse response = electionMapper.toResponse(election);
        return ResponseEntity.created(URI.create("/" + election.getId()))
                .body(response);
    }

    @PatchMapping("/{electionId}/candidates")
    public ResponseEntity<ElectionResponse> updateElectionCandidates(
            @PathVariable UUID electionId,
            @RequestBody @Valid UpdateElectionCandidatesRequest request) {
        Election updatedElection = electionService.updateElectionCandidates(electionId, request.status());
        ElectionResponse response = electionMapper.toResponse(updatedElection);
        return ResponseEntity.ok(response);
    }
}
