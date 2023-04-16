package com.kusitms.tikkle.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByAccountIdAndDate(Long id, String date);
}
