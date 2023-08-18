package com.kusitms.tikkle.sticker;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.memo.Memo;
import com.kusitms.tikkle.memo.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StickerService {

    private final AccountRepository accountRepository;
    private final StickerRepository stickerRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public void postStickerByDtype(CustomUserDetails customUserDetails, Long memoId, String dtype) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMO_NOT_FOUND));
        Sticker sticker = Sticker.createSticker(account, memo, dtype);
        stickerRepository.save(sticker);
    }
}
