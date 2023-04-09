package com.kusitms.tikkle.todo;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.mission.Day;
import com.kusitms.tikkle.participate_mission.ParticipateMission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private boolean isChecked;

    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participate_mission_id")
    private ParticipateMission participateMission;

    public void setAccount(Account account) {
        this.account = account;
        account.getTodoList().add(this);
    }

    public String setDate(Day day) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy/MM/dd");
        if (day == Day.ALL) {   // 매일
            this.date = dateFmt.format(cal.getTime());  // 오늘 날짜
        }else {     // MON, TUE, WED, THUR, FRI, SAT, SUN
            String[] days = {"SUN", "MON", "TUE", "WED", "THUR", "FRI", "SAT"};
            int i = Arrays.asList(days).indexOf(day.toString());
            int date = cal.get(Calendar.DAY_OF_WEEK);
            if (date == i+1) this.date = dateFmt.format(cal.getTime());   // 오늘 요일 = day
        }
        return this.date;
    }

    public static Todo createTodo(Account account, ParticipateMission participateMission) {
        Todo todo = new Todo();
        todo.setAccount(account);
        todo.participateMission = participateMission;
        String date = todo.setDate(participateMission.getMission().getDay());
        todo.isChecked = false;
        if (date != null) return todo;
        else return null;
    }
}
