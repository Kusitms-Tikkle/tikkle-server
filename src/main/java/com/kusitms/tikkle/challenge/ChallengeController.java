package com.kusitms.tikkle.challenge;

import com.kusitms.tikkle.challenge.dto.ChallengeDetailRes;
import com.kusitms.tikkle.challenge.dto.ChallengeRecommendRes;
import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ResponseService responseService;


    @GetMapping("/recommendation")
    public CommonResponse getChallengeRecommendationByAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ChallengeRecommendRes res = challengeService.getChallengeRecommendationByAccount(customUserDetails);
        return responseService.getDataResponse(res);
    }

    @GetMapping("/detail/{id}")
    public CommonResponse getChallengeDetailById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        ChallengeDetailRes res = challengeService.getChallengeDetailById(customUserDetails, id);
        return responseService.getDataResponse(res);
    }


    @GetMapping("/recommendation/test")
    public CommonResponse getChallengeRecommendationByAccountTest() {
        ChallengeRecommendRes res = challengeService.getChallengeRecommendationByAccountTest(2L);
        return responseService.getDataResponse(res);
    }

}