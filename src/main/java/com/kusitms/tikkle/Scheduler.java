package com.kusitms.tikkle;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.todo.Todo;
import com.kusitms.tikkle.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final TodoRepository todoRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")    // 매일 자정
    public void updateProgressbarAll() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1. 스케쥴러 실행합니다>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);     //어제 날짜
        String dateFormat = dateFmt.format(cal.getTime());

        // 오늘 날짜의 모든 사용자 todo 조회
        List<Todo> todoList = todoRepository.findByDate(dateFormat);
        Map<Account, List<Todo>> collect = todoList.stream().collect(Collectors.groupingBy(Todo::getAccount));

        for (Account account : collect.keySet()) {
            // 사용자별 Todo 리스트
            List<Todo> todoListByAccountId = collect.get(account);

            // todo 모드 체크인지 확인
            boolean success = true;
            for (Todo todo : todoListByAccountId) {
                if (!todo.isChecked()) {
                    success = false;
                    break;
                }
            }
            if (success) account.setDateList(dateFormat + ";");  // 성공 진행률 업데이트

            // 프로그래스바 업데이트 (성공, 실패 둘다)
            int max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            int size = 0;
            if(!account.getDateList().equals("")) {
                String[] split = account.getDateList().split(";");
                size = split.length;
            }
            account.setProgressBar((double)size/max*100);
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1. 스케쥴러 끝>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
