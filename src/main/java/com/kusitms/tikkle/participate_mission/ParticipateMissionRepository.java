package com.kusitms.tikkle.participate_mission;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipateMissionRepository extends JpaRepository<ParticipateMission, Long> {
    ParticipateMission findByAccountIdAndMissionId(Long id, Long missionId);
    void deleteByAccountIdAndMissionId(Long id, Long missionId);
}
