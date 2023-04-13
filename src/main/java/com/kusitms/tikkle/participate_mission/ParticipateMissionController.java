package com.kusitms.tikkle.participate_mission;

import com.kusitms.tikkle.configure.response.CommonResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("{id}")
    public CommonResponse deleteParticipateMission(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long id) {
        participateMissionService.deleteParticipateMission(customUserDetails, id);
        return responseService.getSuccessResponse();
    }
}
