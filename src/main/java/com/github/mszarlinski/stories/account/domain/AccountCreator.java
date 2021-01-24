package com.github.mszarlinski.stories.account.domain;

import com.github.mszarlinski.stories.account.FindOrCreateAccountCommand;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class AccountCreator {
    private final AccountRepository accountRepository;
    private final Clock clock;

    AccountCreator(AccountRepository accountRepository, Clock clock) {
        this.accountRepository = accountRepository;
        this.clock = clock;
    }

    public Account createNewAccount(FindOrCreateAccountCommand findOrCreateAccountCommand) {
        return accountRepository.save(new Account(findOrCreateAccountCommand.getUserId(), findOrCreateAccountCommand.getName(), findOrCreateAccountCommand.getLastName(),
                findOrCreateAccountCommand.getEmail(), findOrCreateAccountCommand.getPictureUrl(), clock.instant()));
    }
}
