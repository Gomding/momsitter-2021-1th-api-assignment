package com.momsitter.ui.dto.account;

import com.momsitter.domain.Account;

import java.time.LocalDate;

public class AccountResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String accountId;
    private String password;
    private String email;

    public AccountResponse(Long id, String name, LocalDate dateOfBirth, String gender, String accountId, String password, String email) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
    }

    public static AccountResponse of(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName().getValue(),
                account.getDateOfBirth().getValue(),
                account.getGender().getGenderName(),
                account.getAccountId().getValue(),
                account.getPassword().getValue(),
                account.getEmail().getValue());
    }

    public Long getId() {
        return id;
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
