package com.kusitms.tikkle.account.oAuth;

import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.configure.response.DataResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    private final ResponseService responseService;

    @PostMapping("/login/oauth/kakao")
    public DataResponse<LoginResponse> login(@RequestParam String accessToken) {
        LoginResponse loginResponse = oAuthService.loginWithToken(accessToken);
        return responseService.getDataResponse(loginResponse);
    }
}
