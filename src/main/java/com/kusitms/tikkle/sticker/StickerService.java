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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteSticker(CustomUserDetails customUserDetails, Long stickerId) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Sticker sticker = stickerRepository.findById(stickerId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.STICKER_NOT_FOUND));
        stickerRepository.delete(sticker);
    }

    public Long getStickerCheckByMemoAndDtype(CustomUserDetails customUserDetails, Long memoId, String dtype) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMO_NOT_FOUND));
        Optional<Sticker> sticker = stickerRepository.findByAccountIdAndMemoIdAndDtype(account.getId(), memo.getId(), dtype);
        if (sticker.isPresent()) return sticker.get().getId();
        else return null;
    }

    public Map<String, Long> getStickerReceivedByAccount(CustomUserDetails customUserDetails) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        List<Memo> memos = memoRepository.findByAccountId(account.getId());
        Map<String, Long> collect = memos.stream()
                .flatMap(memo -> memo.getStickerList().stream())
                .collect(Collectors.groupingBy(Sticker::getDtype, Collectors.counting()));
        collect.putIfAbsent("a", 0L);
        collect.putIfAbsent("b", 0L);
        collect.putIfAbsent("c", 0L);
        return collect;
    }
}
