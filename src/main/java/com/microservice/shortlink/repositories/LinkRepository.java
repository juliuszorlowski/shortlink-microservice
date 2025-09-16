package com.microservice.shortlink.repositories;

import com.microservice.shortlink.entities.Link;
import com.microservice.shortlink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByCode(String code);

    @Query("SELECT l FROM Link l WHERE l.user = :user")
    List<Link> getLinksByUser(@Param("user") User user);
}
