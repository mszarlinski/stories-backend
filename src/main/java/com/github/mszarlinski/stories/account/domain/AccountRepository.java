package com.github.mszarlinski.stories.account.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AccountRepository extends Repository<Account, String> {
    Optional<Account> findById(String id);

    Optional<Account> findByEmail(String id);

    Account save(Account account);

}
