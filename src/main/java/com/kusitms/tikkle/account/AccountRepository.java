package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.RoleType;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    //Optional<Account> findByIdAndStatus(Long id, Status valid);
    Optional<Account> findByoAuthId(String oAuthId);
    Optional<Account> findByNickname(String nickname);
    Optional<Account> findByEmailAndStatus(String email, Status valid);
    Optional<Account> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE account a SET " +
            "a.nickname = :nickname, " +
            "a.profile_image_index = :profileImageIndex, " +
            "a.is_checked = :isChecked " +
            "a.role = :roleType " +
            "a.status= :status " +
            "where a.account_id =:id", nativeQuery = true)
    void updateExtraInfoByAccountId(@Param(value = "id") Long id,
                                    @Param(value = "nickname") String nickname,
                                    @Param(value = "profileImageIndex") int profileImageIndex,
                                    @Param(value = "isChecked") boolean isChecked,
                                    @Param(value = "roleType") RoleType roleType,
                                    @Param(value = "status") Status valid);
}
