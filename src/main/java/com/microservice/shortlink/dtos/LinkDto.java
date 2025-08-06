package com.microservice.shortlink.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LinkDto {
    private String url;
    private String code;
    private LocalDateTime created_at;
    private Integer click_count;
}
