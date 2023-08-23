package com.kusitms.tikkle.memo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import com.kusitms.tikkle.sticker.Sticker;
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
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    private String content;

    private String imageUrl;

    private boolean isPrivate;

    @OneToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "memo", orphanRemoval = true,  cascade = CascadeType.REMOVE)
    private List<Sticker> stickerList = new ArrayList<>();

    public static Memo createMemo(Account account, Todo todo, String content, String image) {
        Memo m = new Memo();
        m.account = account;
        m.todo = todo;
        m.content = content;
        m.imageUrl = image;
        m.isPrivate = false;
        return m;
    }

    public void togglePrivate() {
        this.isPrivate = !this.isPrivate;
    }

    public void changeMemo(String content, String image) {
        if (content != null && image != null) {
            this.content = content;
            this.imageUrl = image;
        } else if (content != null) {
            this.content = content;
        } else if (image != null) {
            this.imageUrl = image;
        }
    }
}
