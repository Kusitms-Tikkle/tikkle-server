package com.kusitms.tikkle.sticker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.memo.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    @JsonIgnore
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    private String dtype;

    public void setMemo(Memo memo) {
        this.memo = memo;
        memo.getStickerList().add(this);
    }

    public static Sticker createSticker(Account account, Memo memo, String dtype) {
        Sticker sticker = new Sticker();
        sticker.account = account;
        sticker.setMemo(memo);
        sticker.dtype = dtype;
        return sticker;
    }
}
