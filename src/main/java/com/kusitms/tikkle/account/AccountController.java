package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.dto.LoginRequest;
import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.configure.response.DataResponse;
import com.kusitms.tikkle.configure.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ResponseService responseService;

    @PostMapping("/login/extraInfo/{id}")
    public DataResponse<LoginResponse> login(@PathVariable(value = "id") Long id, @RequestBody LoginRequest request) {
        LoginResponse loginResponse = accountService.setExtraInfo(id, request);
        return responseService.getDataResponse(loginResponse);
    }

    @GetMapping("/login/{nickname}/exists")
    public DataResponse<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        boolean isDuplicated = accountService.checkNicknameDuplicate(nickname);
        return responseService.getDataResponse(isDuplicated);
    }
}
