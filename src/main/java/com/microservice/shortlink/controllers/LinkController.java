package com.microservice.shortlink.controllers;

import com.microservice.shortlink.dtos.CodeDto;
import com.microservice.shortlink.dtos.ShortenLinkRequest;
import com.microservice.shortlink.entities.Link;
import com.microservice.shortlink.mappers.LinkMapper;
import com.microservice.shortlink.repositories.LinkRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@AllArgsConstructor
@RequestMapping()
public class LinkController {
    private final LinkRepository linkRepository;

    @PostMapping("/shorten")
    public ResponseEntity<?> submitLink(@Valid @RequestBody ShortenLinkRequest request) {
        var code = generateRandomString();
        var date = LocalDateTime.now();
        System.out.println(date);
        var link = Link.builder()
                .url(request.getUrl())
                .code(code)
                .createdAt(date)
                .clickCount(0)
                .build();

        linkRepository.save(link);
        CodeDto codeDto = new CodeDto();
        codeDto.setShortened_url(code);
        System.out.println(link.getCreatedAt());

        return ResponseEntity.ok(codeDto);
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
