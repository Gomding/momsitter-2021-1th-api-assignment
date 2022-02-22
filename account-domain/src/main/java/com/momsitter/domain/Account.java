package com.momsitter.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {
    public static final String SITTER_NULL_EXCEPTION_MESSAGE = "시터로 등록시 시터 정보는 null이 될 수 없습니다.";
    public static final String PARENT_NULL_EXCEPTION_MESSAGE = "부모로 등록시 부모 정보는 null이 될 수 없습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private DateOfBirth dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private AccountId accountId;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "sitter_info_id", nullable = true)
    private SitterInfo sitterInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "parent_info_id", nullable = true)
    private ParentInfo parentInfo;

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

    public boolean isInvalidPassword(String password) {
        return !this.password.equals(new Password(password));
    }

    public void updateSitterAccountnfo(Name name, Email email, int minCareAge, int maxCareAge, String aboutMe) {
        this.name = name;
        this.email = email;
        updateSitterInfo(minCareAge, maxCareAge, aboutMe);
    }

    public void updateInfo(Gender gender, Password password, Email email) {
        this.gender = gender;
        this.password = password;
        this.email = email;
    }

    public void updateSitterInfo(int minCareAge, int maxCareAge, String aboutMe) {
        this.sitterInfo.updateInfo(new CareAgeRange(minCareAge, maxCareAge), aboutMe);
    }

    public void updateParentInfo(List<Child> children, String careRequestInfo) {
        this.parentInfo.updateInfo(children, careRequestInfo);
    }

    public boolean isDifferentEmail(String email) {
        return !this.email.getValue().equals(email);
    }

    public void registerSitter(SitterInfo sitterInfo) {
        this.sitterInfo = Objects.requireNonNull(sitterInfo, SITTER_NULL_EXCEPTION_MESSAGE);
    }

    public void registerParent(ParentInfo parentInfo) {
        this.parentInfo = Objects.requireNonNull(parentInfo, PARENT_NULL_EXCEPTION_MESSAGE);
    }

    public boolean isSitter() {
        return Objects.nonNull(this.sitterInfo);
    }

    public boolean isParent() {
        return Objects.nonNull(this.parentInfo);
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

    public ParentInfo getParentInfo() {
        return parentInfo;
    }
}
