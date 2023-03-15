package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.dto.LoginRequest;
import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
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

    @Transactional
    public LoginResponse setExtraInfo(Long id, LoginRequest request) {
        Optional<Account> byId = accountRepository.findByIdAndStatus(id, Status.VALID);

        Account account = null;
        if(byId.isPresent()) account = byId.get();

        accountRepository.updateExtraInfoByAccountId(id, request.getNickname(), request.getProfileImageIndex(), request.isChecked());
        String token = jwtTokenProvider.createToken(account.getEmail(), account.getRole());
        return new LoginResponse("SignIn", account, token);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        Optional<Account> accountOptional = accountRepository.findByNicknameAndStatus(nickname, Status.VALID);
        if (accountOptional.isPresent())
            return true;
        else
            return false;
    }

    @Transactional
    public void toggleAccountValidation(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        account.toggleValid();
    }
}
