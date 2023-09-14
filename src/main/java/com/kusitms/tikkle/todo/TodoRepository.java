package com.kusitms.tikkle.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByAccountIdAndDate(Long id, String date);
    List<Todo> findByDate(String date);

    @Query(value = "SELECT * FROM Todo t " +
            "LEFT JOIN Memo m ON t.todo_id = m.todo_id " +
            "WHERE m.memo_id IS NULL " +
            "AND t.date = :date " +
            "AND t.account_id = :account_id", nativeQuery = true)
    List<Todo> findUnWrittedTodoByAccountIdAndDate(@Param("account_id") Long id, @Param("date") String date);
}
