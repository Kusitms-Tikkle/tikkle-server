package com.kusitms.tikkle.challenge;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.challenge.dto.ChallengeRecommendRes;
import com.kusitms.tikkle.challenge.dto.ChallengeRes;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.mbti.Mbti;
import com.kusitms.tikkle.mbti.MbtiRepository;
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
