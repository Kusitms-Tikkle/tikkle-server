package com.kusitms.tikkle.participate_challenge;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
