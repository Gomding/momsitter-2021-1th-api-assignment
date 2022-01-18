package com.momsitter.ui.dto.account;

import com.momsitter.domain.*;

import java.time.LocalDate;

public class AccountCreateRequest {
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String accountId;
    private String password;
    private String email;

    protected AccountCreateRequest() {
    }

    public AccountCreateRequest(String name, LocalDate dateOfBirth, String gender, String accountId, String password, String email) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
    }

    public Account toEntity() {
        return new Account.Builder()
                .name(new Name(name))
                .dateOfBirth(new DateOfBirth(dateOfBirth))
                .gender(Gender.fromGenderName(gender))
                .accountId(new AccountId(accountId))
                .password(new Password(password))
                .email(new Email(email))
                .build();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
