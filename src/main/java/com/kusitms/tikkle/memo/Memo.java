package com.kusitms.tikkle.memo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    private String content;

    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;
}
