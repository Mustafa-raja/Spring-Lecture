package com.todo.app.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.app.Entities.Todo;

public interface TodoRepo extends JpaRepository<Todo, Long>{
    
}
