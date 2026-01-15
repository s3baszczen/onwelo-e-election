package com.onwelo.election.voting.interfaces;

import com.onwelo.election.voting.application.VoterService;
import com.onwelo.election.voting.domain.model.Voter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/voters")
public class VoterController {

    private final VoterMapper voterMapper;
    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterResponse> registerUserAsNewVoter(@RequestBody @Valid RegisterVoterRequest voterRequest) {
        Voter user = voterMapper.toDomain(voterRequest);
        Voter voter = voterService.registerUserAsNewVoter(user);
        VoterResponse response = voterMapper.toResponse(voter);
        return ResponseEntity.created(URI.create("/" + voter.getId()))
                .body(response);
    }

    @PatchMapping("/{voterId}/status")
    public ResponseEntity<VoterResponse> updateVoterStatus(
            @PathVariable UUID voterId,
            @RequestBody @Valid UpdateVoterStatusRequest request) {
        Voter updatedVoter = voterService.updateVoterStatus(voterId, request.status());
        VoterResponse response = voterMapper.toResponse(updatedVoter);
        return ResponseEntity.ok(response);
    }
}
