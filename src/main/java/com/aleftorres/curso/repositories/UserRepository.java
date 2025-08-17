package com.aleftorres.curso.repositories;

import com.aleftorres.curso.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
