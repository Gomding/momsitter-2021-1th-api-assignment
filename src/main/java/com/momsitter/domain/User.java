package com.momsitter.domain;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String accountId;
    private String password;
    private String email;

    public User() {
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

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
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
