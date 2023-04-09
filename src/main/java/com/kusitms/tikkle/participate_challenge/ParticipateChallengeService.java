package com.kusitms.tikkle.participate_challenge;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.challenge.Challenge;
import com.kusitms.tikkle.challenge.ChallengeRepository;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.mission.Mission;
import com.kusitms.tikkle.mission.MissionRepository;
import com.kusitms.tikkle.participate_mission.ParticipateMission;
import com.kusitms.tikkle.participate_mission.ParticipateMissionRepository;
import com.kusitms.tikkle.todo.Todo;
import com.kusitms.tikkle.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipateChallengeService {

    private final ParticipateChallengeRepository participateChallengeRepository;
    private final AccountRepository accountRepository;
    private final ChallengeRepository challengeRepository;
    private final MissionRepository missionRepository;
    private final ParticipateMissionRepository participateMissionRepository;
    private final TodoRepository todoRepository;


    @Transactional
    public void postParticipateChallenge(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.CHALLENGE_NOT_FOUND));

        // 챌린지 참여
        ParticipateChallenge pc = ParticipateChallenge.createParticipateChallenge(account, challenge);
        participateChallengeRepository.save(pc);

        // 필수 미션 참여
        List<Mission> missionList = missionRepository.findByChallengeIdAndRequired(id, true);
        for(Mission m : missionList) {
            ParticipateMission pm = ParticipateMission.createParticipateMission(account, m, pc);
            participateMissionRepository.save(pm);
            // todo 생성
            Todo todo = Todo.createTodo(account, pm);
            if (todo != null) todoRepository.save(todo);
        }
    }

    public boolean checkParticipateChallenge(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        List<ParticipateChallenge> list = participateChallengeRepository.findByAccountId(account.getId());
        return list.size() == 2 ? true : false;
    }
}
