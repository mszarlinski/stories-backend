package com.github.mszarlinski.stories.account;

import com.github.mszarlinski.stories.account.domain.Account;
import com.github.mszarlinski.stories.account.domain.AccountCreator;
import com.github.mszarlinski.stories.account.domain.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountModuleFacade {

    private final AccountRepository accountRepository;
    private final AccountCreator accountCreator;

    AccountModuleFacade(AccountRepository accountRepository, AccountCreator accountCreator) {
        this.accountRepository = accountRepository;
        this.accountCreator = accountCreator;
    }

    public Optional<UserDto> findAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .map(UserDto::fromAccount);
    }

    public Optional<UserDto> findAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(UserDto::fromAccount);
    }

    public UserDto findOrCreate(FindOrCreateAccountCommand findOrCreateAccountCommand) {
        Account account = accountRepository.findByEmail(findOrCreateAccountCommand.getEmail())
                .orElseGet(() -> accountCreator.createNewAccount(findOrCreateAccountCommand));
        return UserDto.fromAccount(account);
    }
}
