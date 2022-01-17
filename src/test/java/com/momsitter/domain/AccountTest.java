package com.momsitter.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.momsitter.domain.Account.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Account 단위 테스트")
class AccountTest {

    final Name defaultName = new Name("이름");
    final DateOfBirth defaultDateOfBirth = DateOfBirth.of("19920530");
    final Gender defaultGender = Gender.MALE;
    final AccountId defaultAccountId = new AccountId("momsitter2022");
    final Password defaultPassword = new Password("momsitter123!@");
    final Email defaultEmail = new Email("momsitter@gmail.com");

    private Account defaultAccount;

    @BeforeEach
    void setUp() {
        defaultAccount = new Account.Builder()
                .name(defaultName)
                .dateOfBirth(defaultDateOfBirth)
                .gender(defaultGender)
                .accountId(defaultAccountId)
                .password(defaultPassword)
                .email(defaultEmail)
                .build();
    }

    @DisplayName("회원 객체 정상적으로 생성한다.")
    @Test
    void create() {
        // when
        Account account = new Account.Builder()
                .name(defaultName)
                .dateOfBirth(defaultDateOfBirth)
                .gender(defaultGender)
                .accountId(defaultAccountId)
                .password(defaultPassword)
                .email(defaultEmail)
                .build();

        // then
        assertThat(account.getId()).isNull();
        assertThat(account.getName()).isEqualTo(new Name(defaultName.getValue()));
        assertThat(account.getDateOfBirth()).isEqualTo(new DateOfBirth(defaultDateOfBirth.getValue()));
        assertThat(account.getGender()).isEqualTo(Gender.valueOf(defaultGender.name()));
        assertThat(account.getAccountId()).isEqualTo(new AccountId(defaultAccountId.getValue()));
        assertThat(account.getPassword()).isEqualTo(new Password(defaultPassword.getValue()));
        assertThat(account.getEmail()).isEqualTo(new Email(defaultEmail.getValue()));
    }

    @DisplayName("시터 회원 등록 테스트")
    @Nested
    class RegisterSitterTest {
        @DisplayName("회원이 시터회원으로 등록된다.")
        @Test
        void registerSitter() {
            // given
            SitterInfo sitterInfo = new SitterInfo(new CareAgeRange(3, 5), "시터왕 박시터");

            // when
            defaultAccount.registerSitter(sitterInfo);

            // then
            assertThat(defaultAccount.getSitterInfo()).isEqualTo(sitterInfo);
        }

        @DisplayName("회원이 시터회원으로 등록할 때 시터 정보가 null이라면 예외가 발생한다.")
        @Test
        void registerSitterIsNull() {
            // when then
            assertThatThrownBy(() -> defaultAccount.registerSitter(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(SITTER_NULL_EXCEPTION_MESSAGE);
        }
    }

    @DisplayName("부모 회원 등록 테스트")
    @Nested
    class RegisterParentTest {
        @DisplayName("회원이 부모회원으로 등록된다.")
        @Test
        void registerParent() {
            // given
            List<Child> children = Arrays.asList(
                    new Child(1L, DateOfBirth.of("19920530"), Gender.MALE),
                    new Child(2L, DateOfBirth.of("20201030"), Gender.FEMALE)
            );
            String careRequest = "하루종일 코딩할 수 있게 해줄 시터를 찾습니다.";
            ParentInfo parentInfo = new ParentInfo(1L, children, careRequest);

            // when
            defaultAccount.registerParent(parentInfo);

            // then
            assertThat(defaultAccount.getParentInfo()).isEqualTo(parentInfo);
        }

        @DisplayName("부모 회원 등록 시 아이 정보가 0명이라도 된다.")
        @Test
        void registerParentNoChild() {
            // given
            List<Child> children = Collections.emptyList();
            String careRequest = "하루 2시간 아이와 즐겁게 지내주실 분 구합니다!";
            ParentInfo parentInfo = new ParentInfo(1L, children, careRequest);

            // when
            defaultAccount.registerParent(parentInfo);

            // then
            assertThat(defaultAccount.getParentInfo()).isEqualTo(parentInfo);
        }

        @DisplayName("회원이 부모회원으로 등록할 때 부모 정보가 null이라면 예외가 발생한다.")
        @Test
        void registerParentIsNull() {
            // when then
            assertThatThrownBy(() -> defaultAccount.registerParent(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(PARENT_NULL_EXCEPTION_MESSAGE);
        }
    }
}
