package com.kusitms.tikkle.participate_challenge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipateChallengeRepository extends JpaRepository<ParticipateChallenge, Long> {
    List<ParticipateChallenge> findByAccountId(Long accountId);
    ParticipateChallenge findByAccountIdAndChallengeId(Long accountId, Long challengeId);
}
