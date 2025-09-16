package com.microservice.shortlink.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LinkDto {
    private Long id;
    private String url;
    private String code;
    private LocalDateTime createdAt;
    private Integer clickCount;
}
