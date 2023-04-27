package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.dto.AccountInformReq;
import com.kusitms.tikkle.account.dto.AccountInformRes;
import com.kusitms.tikkle.account.dto.LoginRequest;
import com.kusitms.tikkle.account.dto.LoginResponse;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.configure.security.jwt.JwtTokenProvider;
import com.kusitms.tikkle.mbti.Mbti;
import com.kusitms.tikkle.mbti.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.kusitms.tikkle.account.entity.enumtypes.RoleType.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final MbtiRepository mbtiRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse setExtraInfo(Long id, LoginRequest request) {
        Optional<Account> byId = accountRepository.findById(id);

        Account account = null;
        if(byId.isPresent()) account = byId.get();

        // 회원가입 완료
        Account updateAccount = account.updateExtraInfo(request.getNickname(), request.isChecked(), ROLE_USER, Status.VALID);
        Account save = accountRepository.save(updateAccount);
        String token = jwtTokenProvider.createToken(save.getEmail(), save.getRole());
        return new LoginResponse("SignIn", account, token);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        Optional<Account> accountOptional = accountRepository.findByNickname(nickname);
        if (accountOptional.isPresent())
            return true;
        else
            return false;
    }

    @Transactional
    public void toggleAccountValidation(CustomUserDetails customUserDetails, Status status) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        account.toggleValid(status);
    }

    @Transactional
    public void changeAccountInform(CustomUserDetails customUserDetails, AccountInformReq dto) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Mbti mbti = mbtiRepository.findByType(dto.getMbti())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));

        account.changeAccountInform(dto.getNickname(), mbti);
    }

    public AccountInformRes getAccountInform(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        // mbti에 해당하는 이미지 리턴
        Mbti mbti = mbtiRepository.findById(account.getMbti().getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));
        return new AccountInformRes(account.getId(), account.getNickname(), account.getMbti().getLabel(), mbti.getImageUrl());
    }

    @Transactional
    public void postMbti(CustomUserDetails customUserDetails, String type) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Mbti mbti = mbtiRepository.findByType(type)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));
        account.setAccountMbti(mbti);
    }

}
