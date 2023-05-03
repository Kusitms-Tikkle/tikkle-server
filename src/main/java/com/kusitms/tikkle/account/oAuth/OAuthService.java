package com.kusitms.tikkle.account.oAuth;

import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.json.JSONObject;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse loginWithToken(String token) {
        CheckLogin checkLogin = validateSignInByToken(token);
        Account account = checkLogin.getAccount();
        if (checkLogin.registered) {
            String accessToken = jwtTokenProvider.createToken(account.getEmail(), account.getRole());
            //로그아웃한 사용자는 Status.valid로 변환
            if (account.getStatus() == Status.LOGOUT) account.toggleValid(Status.VALID);
            return new LoginResponse("signIn", account, accessToken);
        } else{
            return new LoginResponse("signUp", account, null);
        }
    }

    private CheckLogin validateSignInByToken(String token) {
        //accessToken으로 userProfile정보 얻기
        KakaoUserInfo kakaoUserInfo = getUserAttributesByToken(token);

        String kakao_id = "kakao_" + kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();
        String kakao_nickname = kakaoUserInfo.getNickname();

        Account account = null;
        boolean registered = false;
        //중복회원 확인
        Optional<Account> byKakaoId = accountRepository.findByoAuthId(kakao_id);
        if(byKakaoId.isPresent()) {
            account = byKakaoId.get();
            registered = true;
        } else{
            Account newAccount = Account.createAccount(kakao_id, email, kakao_nickname);
            Account save = accountRepository.save(newAccount);
            account = save;
        }
        return new CheckLogin(registered, account);
    }

    private KakaoUserInfo getUserAttributesByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JSONObject body = new JSONObject(response.getBody());
        Long id = body.getLong("id");
        String email = body.getJSONObject("kakao_account").getString("email");
        String nickname = body.getJSONObject("properties").getString("nickname");

        if(id == null || email == null || nickname == null)
            throw new CustomException(CustomExceptionStatus.OAUTH_EMPTY_INFORM);

        return new KakaoUserInfo(id, nickname, email);
    }

    @Data
    @AllArgsConstructor
    private class CheckLogin {
        boolean registered;
        Account account;
    }
}
