package com.onwelo.election.voter.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/voters")
public class VoterController {

    @PostMapping
    public ResponseEntity<UUID> registerNewVoter(@RequestBody @Valid RegisterVoterRequest voter) {
        System.out.println("user: " + voter.getFirstName());
        return ResponseEntity.created(URI.create("/" + UUID.randomUUID())).build();
    }
}
