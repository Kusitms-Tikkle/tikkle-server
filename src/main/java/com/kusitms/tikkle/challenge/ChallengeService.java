package com.kusitms.tikkle.challenge;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.challenge.dto.ChallengeDetailRes;
import com.kusitms.tikkle.challenge.dto.ChallengeRecommendRes;
import com.kusitms.tikkle.challenge.dto.ChallengeRes;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.mbti.Mbti;
import com.kusitms.tikkle.mbti.MbtiRepository;
import com.kusitms.tikkle.mission.Day;
import com.kusitms.tikkle.mission.Mission;
import com.kusitms.tikkle.mission.MissionRepository;
import com.kusitms.tikkle.mission.dto.MissionRes;
import com.kusitms.tikkle.participate.Participate;
import com.kusitms.tikkle.participate.ParticipateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final AccountRepository accountRepository;
    private final MbtiRepository mbtiRepository;
    private final MissionRepository missionRepository;
    private final ParticipateRepository participateRepository;


    public ChallengeRecommendRes getChallengeRecommendationByAccount(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Mbti mbti = mbtiRepository.findById(account.getMbti().getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));

        String [] strArr = mbti.getType().split("");
        List<String> typeList = new ArrayList<>(Arrays.asList(strArr));
        List<ChallengeRes> challengeList = new ArrayList<>();

        for(String type : typeList) {
            Challenge challenge = challengeRepository.findByMbtiContaining(type);
            if(!challengeList.contains(challenge) && challenge!=null)  {
                challengeList.add(new ChallengeRes(challenge.getId(), challenge.getImageUrl(), challenge.getShortIntro()));
            }
        }

        return new ChallengeRecommendRes(account.getNickname(), mbti.getLabel(), mbti.getImageUrl(), mbti.getIntro(),
                challengeList.parallelStream().distinct().collect(Collectors.toList()));
    }

    public ChallengeDetailRes getChallengeDetailById(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));   // 유효한 사용자인지 체크

        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.CHALLENGE_NOT_FOUND));

        List<Mission> list = missionRepository.findByChallengeId(id);

        List<MissionRes> resList = new ArrayList<>();
        for(Mission m : list) {
            Participate p = participateRepository.findByAccountIdAndMissionId(account.getId(), m.getId());
            if (m.getDay() == Day.ALL && p == null) resList.add(new MissionRes(m.getId(), false, m.getTitle(), true, m.getDay()));
            if (m.getDay() == Day.ALL && p != null) resList.add(new MissionRes(m.getId(), true, m.getTitle(), true, m.getDay()));
            if (m.getDay() != Day.ALL && p == null) resList.add(new MissionRes(m.getId(), false, m.getTitle(), false, m.getDay()));
            if (m.getDay() != Day.ALL && p != null) resList.add(new MissionRes(m.getId(), true, m.getTitle(), false, m.getDay()));
        }
        return new ChallengeDetailRes(challenge.getId(), challenge.getImageUrl(), challenge.getTitle(), challenge.getIntro(), resList);
    }


    // 프론트 요청
    public ChallengeRecommendRes getChallengeRecommendationByAccountTest(Long id) {
        Account account = accountRepository.findByIdAndStatus(id, Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Mbti mbti = mbtiRepository.findById(account.getMbti().getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));

        String [] strArr = mbti.getType().split("");
        List<String> typeList = new ArrayList<>(Arrays.asList(strArr));
        List<ChallengeRes> challengeList = new ArrayList<>();

        for(String type : typeList) {
            Challenge challenge = challengeRepository.findByMbtiContaining(type);
            if(!challengeList.contains(challenge) && challenge!=null)  {
                challengeList.add(new ChallengeRes(challenge.getId(), challenge.getImageUrl(), challenge.getShortIntro()));
            }
        }

        return new ChallengeRecommendRes(account.getNickname(), mbti.getLabel(), mbti.getImageUrl(), mbti.getIntro(),
                challengeList.parallelStream().distinct().collect(Collectors.toList()));
    }

}