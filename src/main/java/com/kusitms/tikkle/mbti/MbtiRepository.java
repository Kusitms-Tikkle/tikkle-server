package com.kusitms.tikkle.mbti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Optional<Mbti> findByType(String type);
}
