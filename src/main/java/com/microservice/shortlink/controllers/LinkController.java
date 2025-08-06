package com.microservice.shortlink.controllers;

import com.microservice.shortlink.dtos.CodeDto;
import com.microservice.shortlink.dtos.ShortenLinkRequest;
import com.microservice.shortlink.entities.Link;
import com.microservice.shortlink.repositories.LinkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping()
public class LinkController {
    @Value("${app-url}")
    private String appUrl;

    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> submitLink(@Valid @RequestBody ShortenLinkRequest request) {
        var code = generateRandomString();
        var date = LocalDateTime.now();
        var link = Link.builder()
                .url(request.getUrl())
                .code(code)
                .createdAt(date)
                .clickCount(0)
                .build();

        linkRepository.save(link);
        CodeDto codeDto = new CodeDto();
        codeDto.setShortened_url(appUrl + code);

        return ResponseEntity.ok(codeDto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirectToLink(@PathVariable String code) {
        var link = linkRepository.findByCode(code).orElse(null);
        if (link == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);
        var url = link.getUrl();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    private String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
