package com.microservice.shortlink.services;

import com.microservice.shortlink.dtos.LinkDto;
import com.microservice.shortlink.dtos.ShortenLinkRequest;
import com.microservice.shortlink.entities.Link;
import com.microservice.shortlink.exceptions.LinkNotFoundException;
import com.microservice.shortlink.mappers.LinkMapper;
import com.microservice.shortlink.repositories.LinkRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;
    private final AuthService authService;
    private final LinkMapper linkMapper;

    public String createSortLink(ShortenLinkRequest request) {
        var code = generateRandomString();
        var date = LocalDateTime.now();
        var user = authService.getCurrentUser();
        var link = Link.builder()
                .url(request.getUrl())
                .code(code)
                .createdAt(date)
                .clickCount(0)
                .user(user)
                .build();

        linkRepository.save(link);

        return code;
    }

    @Transactional
    public Link findLinkAndIncrementClickCount(String code) {
        var link = linkRepository.findByCode(code).orElseThrow(LinkNotFoundException::new);

        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);

        return link;
    }

    public List<LinkDto> getAllLinks() {
        var user = authService.getCurrentUser();
        var links = linkRepository.getLinksByUser(user);
        return links.stream().map(linkMapper::toDto).toList();
    }

    public LinkDto getLink(String code) {
        var link = linkRepository
                .findByCode(code)
                .orElseThrow(LinkNotFoundException::new);

        var user = authService.getCurrentUser();
        if (!link.createdBy(user)) {
            throw new AccessDeniedException("This is not your link");
        }

        return linkMapper.toDto(link);
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
