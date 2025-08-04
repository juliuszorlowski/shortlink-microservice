package com.microservice.shortlink.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "shortened_url")
    private String shortenedUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "click_count")
    private Integer clickCount;
}
