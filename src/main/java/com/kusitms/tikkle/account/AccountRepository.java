package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdAndStatus(Long id, Status valid);
    Optional<Account> findByoAuthId(String oAuthId);
    Optional<Account> findByEmail(String email);
    Optional<Account>findByNicknameAndStatus(String nickname, Status valid);

    boolean existsByNickname(String email);

    @Modifying
    @Query(value = "UPDATE account a SET " +
            "a.nickname = :nickname, " +
            "a.profile_image_index = :profileImageIndex, " +
            "a.is_checked = :isChecked " +
            "where a.account_id =:id", nativeQuery = true)
    void updateExtraInfoByAccountId(@Param(value = "id") Long id,
                                    @Param(value = "nickname") String nickname,
                                    @Param(value = "profileImageIndex") int profileImageIndex,
                                    @Param(value = "isChecked") boolean isChecked);
}
