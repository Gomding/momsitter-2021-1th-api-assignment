package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.momsitter.domain.AccountId.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AccountId(회원 계정 ID 값 객체) 단위 테스트")
public class AccountIdTest {

    @DisplayName("AccountId 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("회원 계정 ID 정상 생성된다.")
        @Test
        void create() {
            // given
            String value = "momsitter2022";

            // when
            AccountId accountId = new AccountId(value);

            // then
            assertThat(accountId).isEqualTo(new AccountId(value));
        }

        @DisplayName("회원의 계정 ID가 null이면 예외가 발생한다.")
        @Test
        void createAccountIdIsNull() {
            // when then
            assertThatThrownBy(() -> new AccountId(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원의 계정 ID가 6글자 미만이면 예외가 발생한다.")
        @Test
        void createMinLength() {
            // given
            String value = "fiveL";

            // when then
            assertThatThrownBy(() -> new AccountId(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(String.format(LENGTH_EXCEPTION_MESSAGE_FORMAT, value.length()));
        }

        @DisplayName("회원의 계정 ID가 20글자를 초과하면 예외가 발생한다.")
        @Test
        void createMaxLength() {
            // given
            String value = "TooMuuuuuuuuuuuchLong";

            // when
            assertThatThrownBy(() -> new AccountId(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(String.format(LENGTH_EXCEPTION_MESSAGE_FORMAT, value.length()));
        }

        @DisplayName("회원의 계정 ID에 공백(\" \")이 포함되면 예외가 발생한다.")
        @Test
        void createContainsBlank() {
            // given
            String value = "have  Blank";

            // when
            assertThatThrownBy(() -> new AccountId(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(BLANK_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원의 계정 ID에 영어 또는 숫자외의 값이 들어가면 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"mom2022한글이들어간아이디", "momsitter2022!!!#@"})
        void createMissMatchPattern(String value) {
            // when then
            assertThatThrownBy(() -> new AccountId(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PATTERN_EXCEPTION_MESSAGE);
        }
    }
}
