package com.kusitms.tikkle.memo;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.s3.S3Uploader;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.memo.dto.MemoDto;
import com.kusitms.tikkle.memo.dto.MemoRequestDto;
import com.kusitms.tikkle.memo.dto.MemoWithTodoResponseDto;
import com.kusitms.tikkle.sticker.Sticker;
import com.kusitms.tikkle.todo.Todo;
import com.kusitms.tikkle.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final AccountRepository accountRepository;
    private final TodoRepository todoRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void postMemo(CustomUserDetails customUserDetails, MemoRequestDto memoRequestDto, MultipartFile multipartFile) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Todo todo = todoRepository.findById(memoRequestDto.getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.TODO_NOT_FOUND));

        Memo memo = null;
        if(multipartFile!=null) {
            String image = s3Uploader.uploadImage(multipartFile);
            memo = Memo.createMemo(account, todo, memoRequestDto.getContent(), image);
        } else {
            memo = Memo.createMemo(account, todo, memoRequestDto.getContent(), null);
        }
        memoRepository.save(memo);
    }

    @Transactional
    public void toggleMemoPrivate(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMO_NOT_FOUND));
        memo.togglePrivate();
    }

    @Transactional
    public void deleteMemo(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMO_NOT_FOUND));
        memoRepository.delete(memo);
    }

    @Transactional
    public void changeMemoContent(CustomUserDetails customUserDetails, MemoRequestDto memoRequestDto, MultipartFile multipartFile) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        Memo memo = memoRepository.findById(memoRequestDto.getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.MEMO_NOT_FOUND));
        String image = null;
        if (multipartFile!=null) {
            image = s3Uploader.uploadImage(multipartFile);
        }
        memo.changeMemo(memoRequestDto.getContent(), image);
    }

    public List<MemoWithTodoResponseDto> getMyMemosByDate(CustomUserDetails customUserDetails, String date) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        List<Todo> todos = todoRepository.findByAccountIdAndDate(account.getId(), date);
        List<MemoWithTodoResponseDto> responseDtos = new ArrayList<>();
        for(Todo todo : todos) {
            MemoWithTodoResponseDto dto = new MemoWithTodoResponseDto(todo.getId(), todo.isChecked(), todo.getParticipateMission().getMission().getTitle(), todo.getParticipateMission().getMission().getChallenge().getColor());
            Optional<Memo> memo = memoRepository.findByTodoId(todo.getId());
            if(memo.isPresent()) {
                Memo m = memo.get();
                Map<String, Long> collect = m.getStickerList().stream()
                        .collect(Collectors.groupingBy(Sticker::getDtype, Collectors.counting()));
                MemoDto memoDto = new MemoDto(m.getId(), m.getContent(), m.getImageUrl(), m.isPrivate(), collect.get("a"), collect.get("b"), collect.get("c"));
                dto.setMemo(memoDto);
            }
            responseDtos.add(dto);
        }
        return responseDtos;
    }

}
