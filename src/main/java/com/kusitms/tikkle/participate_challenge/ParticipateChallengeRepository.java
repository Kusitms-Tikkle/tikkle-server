package com.kusitms.tikkle.participate_challenge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipateChallengeRepository extends JpaRepository<ParticipateChallenge, Long> {
    List<ParticipateChallenge> findByAccountId(Long accountId);
    ParticipateChallenge findByAccountIdAndChallengeId(Long accountId, Long challengeId);
    Optional<ParticipateChallenge> findByChallengeIdAndAccountId(Long challengeId, Long accountId);
}
