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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (account.getMbti()!=null) {
            Mbti mbti = mbtiRepository.findById(account.getMbti().getId()).get();
            return new AccountInformRes(account.getId(), account.getNickname(), account.getMbti().getLabel(), mbti.getImageUrl());
        } else return new AccountInformRes(account.getId(), account.getNickname(), null, null);
    }

    @Transactional
    public void postMbti(CustomUserDetails customUserDetails, String type) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        // x(타인 중심 소비형),y(타인 영향 소비형-긍정) 제거
        // 챌린지는 z(타인 영향 소비형-부정)만 존재
        if(type.contains("x") || type.contains("y")) {
            type = type.replace("x", "");
            type = type.replace("y", "");
        }
        System.out.println(">>>>>>>>>>>>>type: " + type);
        Mbti mbti = mbtiRepository.findByType(type)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MBTI_NOT_FOUND));
        account.setAccountMbti(mbti);
    }


    public double getProgressbar(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        return account.getProgressBar();
    }

    public List<Boolean> getAccountSticker(CustomUserDetails customUserDetails, String startDate) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        String[] dateArray= account.getDateList().split(";");

        List<Boolean> stickers = new ArrayList<>();
        for (int i=0; i<7; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(startDate));
                c.add(Calendar.DATE, i);
                String date = sdf.format(c.getTime());
                System.out.println("[PRINT DATE] " + date);
                if (Arrays.stream(dateArray).anyMatch(s -> s.equals(date))) {
                    stickers.add(true);
                } else stickers.add(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return stickers;
    }

    public boolean checkMbtiByAccountId(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Mbti mbti = account.getMbti();
        if (mbti==null) return false;
        else return true;
    }

    public void deleteAccount(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        accountRepository.delete(account);
    }
}
