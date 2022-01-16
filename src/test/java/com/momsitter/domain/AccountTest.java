package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Account 단위 테스트")
public class AccountTest {

    @DisplayName("사용자 객체 생성을 테스트한다.")
    @Nested
    class CreateTest {
        @DisplayName("사용자 객체 정상적으로 생성한다.")
        @Test
        void create() {
            // given
            String name = "이름";
            LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
            Gender gender = Gender.MALE;
            String accountId = "momsitter2022";
            String password = "momsitterpw!@";
            String email = "momsitter@gmail.com";

            // when
            Account account = new Account.Builder()
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .gender(gender)
                    .accountId(accountId)
                    .password(password)
                    .email(email)
                    .build();

            // then
            assertThat(account.getId()).isNull();
            assertThat(account.getName()).isEqualTo(name);
            assertThat(account.getDateOfBirth()).isEqualTo(dateOfBirth);
            assertThat(account.getGender()).isEqualTo(gender);
            assertThat(account.getAccountId()).isEqualTo(accountId);
            assertThat(account.getPassword()).isEqualTo(password);
            assertThat(account.getEmail()).isEqualTo(email);
        }

        @DisplayName("사용자의 계정 ID가 null이면 예외가 발생한다.")
        @Test
        void createAccountIdIsNull() {
            // given
            String name = "이름";
            LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
            Gender gender = Gender.MALE;
            String accountId = null;
            String password = "momsitterpw!@";
            String email = "momsitter@gmail.com";

            // when
            assertThatThrownBy(() -> new Account.Builder()
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .gender(gender)
                    .accountId(accountId)
                    .password(password)
                    .email(email)
                    .build()).isInstanceOf(NullPointerException.class)
                    .hasMessage("사용자의 계정 ID는 null일 수 없습니다.");
        }

        @DisplayName("사용자의 계정 ID가 6글자 미만이면 예외가 발생한다.")
        @Test
        void createAccountIdMinLength() {
            // given
            String name = "이름";
            LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
            Gender gender = Gender.MALE;
            String accountId = "fiveL";
            String password = "momsitterpw!@";
            String email = "momsitter@gmail.com";

            // when
            assertThatThrownBy(() -> new Account.Builder()
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .gender(gender)
                    .accountId(accountId)
                    .password(password)
                    .email(email)
                    .build()).isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용자의 계정 ID는 6글자 이상 20글자 이하여야 합니다.");
        }

        @DisplayName("사용자의 계정 ID가 20글자를 초과하면 예외가 발생한다.")
        @Test
        void createAccountIdMaxLength() {
            // given
            String name = "이름";
            LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
            Gender gender = Gender.MALE;
            String accountId = "thisAccountIdIsTooMuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuchLength";
            String password = "momsitterpw!@";
            String email = "momsitter@gmail.com";

            // when
            assertThatThrownBy(() -> new Account.Builder()
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .gender(gender)
                    .accountId(accountId)
                    .password(password)
                    .email(email)
                    .build()).isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용자의 계정 ID는 6글자 이상 20글자 이하여야 합니다.");
        }

        @DisplayName("사용자의 계정 ID에 공백(\" \")이 포함되면 예외가 발생한다.")
        @Test
        void createAccountIdContainBlank() {
            // given
            String name = "이름";
            LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
            Gender gender = Gender.MALE;
            String accountId = "have  Blank";
            String password = "momsitterpw!@";
            String email = "momsitter@gmail.com";

            // when
            assertThatThrownBy(() -> new Account.Builder()
                    .name(name)
                    .dateOfBirth(dateOfBirth)
                    .gender(gender)
                    .accountId(accountId)
                    .password(password)
                    .email(email)
                    .build()).isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("사용자의 계정 ID는 공백을 포함할 수 없습니다.");
        }
    }

}
