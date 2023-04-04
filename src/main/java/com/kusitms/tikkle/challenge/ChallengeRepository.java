package com.kusitms.tikkle.challenge;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Challenge findByMbtiContaining(String type);
}
