package com.kusitms.tikkle.participate;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipateRepository extends JpaRepository<Participate, Long> {
    Participate findByAccountIdAndMissionId(Long id, Long missionId);
}
