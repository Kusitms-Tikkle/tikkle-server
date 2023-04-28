package com.kusitms.tikkle.participate_challenge;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/participate/challenge")
public class ParticipateChallengeController {

    private final ParticipateChallengeService participateChallengeService;
    private final ResponseService responseService;

    @PostMapping("{id}")
    public CommonResponse postParticipateChallenge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        participateChallengeService.postParticipateChallenge(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @DeleteMapping("{id}")
    public CommonResponse deleteParticipateChallenge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        participateChallengeService.deleteParticipateChallenge(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

    @GetMapping("/check")
    public CommonResponse checkParticipateChallenge(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        boolean b = participateChallengeService.checkParticipateChallenge(customUserDetails);
        return responseService.getDataResponse(b);
    }

    @GetMapping("/check/{id}")
    public CommonResponse getChallengeParticipateCheckById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "id") Long id) {
        boolean b = participateChallengeService.getChallengeParticipateCheckById(customUserDetails, id);
        return responseService.getDataResponse(b);
    }
}
