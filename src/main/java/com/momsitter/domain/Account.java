package com.momsitter.domain;

import java.util.Objects;

public class Account {
    public static final String SITTER_NULL_EXCEPTION_MESSAGE = "시터로 등록시 시터 정보는 null이 될 수 없습니다.";

    private Long id;
    private Name name;
    private DateOfBirth dateOfBirth;
    private Gender gender;
    private AccountId accountId;
    private Password password;
    private Email email;
    private SitterInfo sitterInfo;

    protected Account() {
    }

    public static class Builder {
        private Long id;
        private Name name;
        private DateOfBirth dateOfBirth;
        private Gender gender;
        private AccountId accountId;
        private Password password;
        private Email email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(Name name) {
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

        public Builder email(Email email) {
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

    public void registerSitter(SitterInfo sitterInfo) {
        this.sitterInfo = Objects.requireNonNull(sitterInfo, SITTER_NULL_EXCEPTION_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
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

    public Email getEmail() {
        return email;
    }

    public SitterInfo getSitterInfo() {
        return sitterInfo;
    }
}
