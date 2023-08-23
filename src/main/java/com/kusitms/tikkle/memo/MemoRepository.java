package com.kusitms.tikkle.memo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<Memo> findByTodoId(Long id);
    List<Memo> findByAccountId(Long id);
}
