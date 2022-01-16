package com.momsitter.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Account {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String accountId;
    private String password;
    private String email;

    protected Account() {
    }

    public static class Builder {
        private Long id;
        private String name;
        private LocalDate dateOfBirth;
        private Gender gender;
        private String accountId;
        private String password;
        private String email;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder password(String passwod) {
            this.password = passwod;
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
        validateAccountId(this.accountId);
    }

    public void validateAccountId(String accountId) {
        Objects.requireNonNull(accountId, "사용자의 계정 ID는 null일 수 없습니다.");
        if (accountId.length() < 6 || accountId.length() > 20) {
            throw new IllegalArgumentException("사용자의 계정 ID는 6글자 이상 20글자 이하여야 합니다.");
        }
        if (accountId.contains(" ")) {
            throw new IllegalArgumentException("사용자의 계정 ID는 공백을 포함할 수 없습니다.");
        }
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

    public Gender getGender() {
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
