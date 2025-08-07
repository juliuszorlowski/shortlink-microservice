package com.microservice.shortlink.controllers;

import com.microservice.shortlink.dtos.CodeDto;
import com.microservice.shortlink.dtos.ShortenLinkRequest;
import com.microservice.shortlink.services.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping()
public class LinkController {
    @Value("${app-url}")
    private String appUrl;

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> submitLink(@Valid @RequestBody ShortenLinkRequest request) {
        var code = linkService.createSortLink(request);

        var codeDto = new CodeDto();
        codeDto.setShortened_url(appUrl + code);

        return ResponseEntity.created(URI.create(appUrl + code)).body(codeDto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirectToLink(@PathVariable String code) {
        var link = linkService.findLinkAndIncrementClickCount(code);
        if (link == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(link.getUrl()))
                .build();
    }
}
