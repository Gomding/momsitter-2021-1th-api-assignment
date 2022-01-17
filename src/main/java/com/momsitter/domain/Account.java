package com.momsitter.domain;

import java.time.LocalDate;

public class Account {
    private Long id;
    private String name;
    private DateOfBirth dateOfBirth;
    private Gender gender;
    private AccountId accountId;
    private Password password;
    private String email;

    protected Account() {
    }

    public static class Builder {
        private Long id;
        private String name;
        private DateOfBirth dateOfBirth;
        private Gender gender;
        private AccountId accountId;
        private Password password;
        private String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder accountId(AccountId accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    private Account(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
        this.accountId = builder.accountId;
        this.password = builder.password;
        this.email = builder.email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Password getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
