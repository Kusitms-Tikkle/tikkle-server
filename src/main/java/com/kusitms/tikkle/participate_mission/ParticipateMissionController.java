package com.kusitms.tikkle.participate_mission;

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
@RequestMapping(value = "/participate/mission")
public class ParticipateMissionController {

    private final ParticipateMissionService participateMissionService;
    private final ResponseService responseService;

    @PostMapping("{id}")
    public CommonResponse postParticipateMission(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long id) {
        participateMissionService.postParticipateMission(customUserDetails, id);
        return responseService.getSuccessResponse();
    }

}
