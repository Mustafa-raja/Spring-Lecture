package com.todo.app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.Entities.Userr;
import java.util.Optional;


public interface UserRepo extends JpaRepository<Userr,Long> {
    Optional<Userr> findByEmail(String email);
}
