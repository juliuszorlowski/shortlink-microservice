package com.microservice.shortlink.controllers;

import com.microservice.shortlink.dtos.ShortenLinkRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping()
public class LinkController {
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenLink(@Valid @RequestBody ShortenLinkRequest request) {

        return ResponseEntity.ok(request);
    }
}
