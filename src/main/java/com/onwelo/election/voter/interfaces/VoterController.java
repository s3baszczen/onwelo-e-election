package com.onwelo.election.voter.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voters")
public class VoterController {

    @PostMapping
    public ResponseEntity<UUID> registerNewVoter() {
        return ResponseEntity.created(URI.create("/" + UUID.randomUUID())).build();
    }
}
