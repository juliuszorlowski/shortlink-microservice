package com.microservice.shortlink.repositories;

import com.microservice.shortlink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
