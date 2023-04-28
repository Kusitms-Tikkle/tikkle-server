package com.kusitms.tikkle.todo;

import com.kusitms.tikkle.account.AccountRepository;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.configure.response.exception.CustomException;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import com.kusitms.tikkle.configure.security.authentication.CustomUserDetails;
import com.kusitms.tikkle.todo.dto.todoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final AccountRepository accountRepository;


    public List<todoRes> getTodoByDate(CustomUserDetails customUserDetails, String date) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        List<Todo> todoList = todoRepository.findByAccountIdAndDate(account.getId(), date);
        List<todoRes> collect = todoList.stream().map(t -> new todoRes(t.getId(), t.getParticipateMission().getMission().getTitle(), t.isChecked(), t.getParticipateMission().getMission().getChallenge().getColor()))
                .collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public void toggleTodoByMissionId(CustomUserDetails customUserDetails, Long id) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getEmail(), Status.VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.TODO_NOT_FOUND));

        todo.toggleIsCheck();
    }

}
