package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.dto.LoginRequest;
import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.configure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse setExtraInfo(Long id, LoginRequest request) {
        Optional<Account> byId = accountRepository.findById(id);

        Account account = null;
        if(byId.isPresent()) account = byId.get();

        accountRepository.updateExtraInfoByAccountId(id, request.getNickname(), request.getProfileImageIndex(), request.isChecked());
        String token = jwtTokenProvider.createToken(account.getEmail(), account.getRole());
        return new LoginResponse("SignIn", account, token);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return accountRepository.existsByNickname(nickname);
    }
}
