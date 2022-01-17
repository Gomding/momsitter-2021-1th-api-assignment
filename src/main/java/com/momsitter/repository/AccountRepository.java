package com.momsitter.repository;

import com.momsitter.domain.Account;
import com.momsitter.domain.AccountId;
import com.momsitter.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(Email email);
    boolean existsByAccountId(AccountId accountId);
}
