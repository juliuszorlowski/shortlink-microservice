package com.microservice.shortlink.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortenLinkRequest {
    @NotBlank(message = "URL is required")
    @URL(message = "URL must be valid")
    private String url;
}
