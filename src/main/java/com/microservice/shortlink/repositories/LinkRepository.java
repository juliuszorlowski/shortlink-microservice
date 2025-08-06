package com.microservice.shortlink.repositories;

import com.microservice.shortlink.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
