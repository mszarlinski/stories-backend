package com.github.mszarlinski.stories.account.domain;

import com.github.mszarlinski.stories.account.FindOrCreateAccountCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountCreator {
    private final AccountRepository accountRepository;

    AccountCreator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createNewAccount(FindOrCreateAccountCommand findOrCreateAccountCommand) {
        return accountRepository.save(new Account(findOrCreateAccountCommand.getName(), findOrCreateAccountCommand.getLastName(), findOrCreateAccountCommand.getEmail()));
    }
}
