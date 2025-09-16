package com.microservice.shortlink.controllers;

import com.microservice.shortlink.dtos.CodeDto;
import com.microservice.shortlink.dtos.ErrorDto;
import com.microservice.shortlink.dtos.LinkDto;
import com.microservice.shortlink.dtos.ShortenLinkRequest;
import com.microservice.shortlink.exceptions.LinkNotFoundException;
import com.microservice.shortlink.services.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(link.getUrl()))
                .build();
    }

    @GetMapping("/links")
    public List<LinkDto> getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/links/{code}")
    public LinkDto getLink(@PathVariable("code") String code) {
        return linkService.getLink(code);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ErrorDto> handleLinkNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Link not found."));
    }
}
