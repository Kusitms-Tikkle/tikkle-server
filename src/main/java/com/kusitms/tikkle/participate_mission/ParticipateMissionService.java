package com.kusitms.tikkle.participate_mission;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.mission.Mission;
import com.kusitms.tikkle.mission.MissionRepository;
import com.kusitms.tikkle.participate_challenge.ParticipateChallenge;
import com.kusitms.tikkle.participate_challenge.ParticipateChallengeRepository;
import com.kusitms.tikkle.todo.Todo;
import com.kusitms.tikkle.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipateMissionService {

    private final ParticipateMissionRepository participateMissionRepository;
    private final AccountRepository accountRepository;
    private final MissionRepository missionRepository;
    private final ParticipateChallengeRepository participateChallengeRepository;
    private final TodoRepository todoRepository;


    @Transactional
    public void postParticipateMission(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MISSION_NOT_FOUND));

        ParticipateChallenge pc = participateChallengeRepository.findByChallengeId(mission.getChallenge().getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PARTICIPATE_CHALLENGE_NOT_FOUND));

        ParticipateMission pm = ParticipateMission.createParticipateMission(account, mission, pc);
        participateMissionRepository.save(pm);
        // todo 생성
        Todo todo = Todo.createTodo(account, pm);
        if (todo != null) todoRepository.save(todo);

    }
}
