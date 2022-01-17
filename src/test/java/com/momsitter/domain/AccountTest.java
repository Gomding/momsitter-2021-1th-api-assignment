package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Account 단위 테스트")
public class AccountTest {

    final Name defaultName = new Name("이름");
    final DateOfBirth defaultDateOfBirth = DateOfBirth.of("19920530");
    final Gender defaultGender = Gender.MALE;
    final AccountId defaultAccountId = new AccountId("momsitter2022");
    final Password defaultPassword = new Password("momsitter123!@");
    final Email defaultEmail = new Email("momsitter@gmail.com");

    @DisplayName("회원 객체 생성을 테스트한다.")
    @Nested
    class CreateTest {
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
    }
}
