package com.kusitms.tikkle.participate_mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.mission.Mission;
import com.kusitms.tikkle.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @JsonIgnore
    @OneToMany(mappedBy = "participateMission")
    private List<Todo> todoList = new ArrayList<>();
}
