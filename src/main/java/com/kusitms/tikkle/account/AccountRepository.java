package com.kusitms.tikkle.account;

import com.kusitms.tikkle.account.entity.Account;
import com.kusitms.tikkle.account.entity.enumtypes.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdAndStatus(Long id, Status valid);
    Optional<Account> findByoAuthId(String oAuthId);
    Optional<Account> findByNickname(String nickname);
    Optional<Account> findByEmailAndStatus(String email, Status valid);
    Optional<Account> findByEmail(String email);
}
