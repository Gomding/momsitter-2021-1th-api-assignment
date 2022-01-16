package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.momsitter.domain.Password.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Password(회원 계정 비밀번호 값 객체) 단위 테스트")
public class PasswordTest {

    @DisplayName("Password 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("회원 계정 비밀번호 객체 정상 생성된다.")
        @Test
        void create() {
            // given
            String value = "momsitter2022!@";

            // when
            Password password = new Password(value);

            // then
            assertThat(password).isEqualTo(new Password(value));
        }

        @DisplayName("회원의 계정 비밀번호가 null이면 예외가 발생한다.")
        @Test
        void createValueIsNull() {
            // when then
            assertThatThrownBy(() -> new Password(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 계정 비밀번호가 6글자 미만이라면 예외가 발생한다.")
        @Test
        void createMinLength() {
            // given
            String value = "5len!";

            // when then
            assertThatThrownBy(() -> new Password(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(LENGTH_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 계정 비밀번호가 16글자를 초과하면 예외가 발생한다.")
        @Test
        void createMaxLength() {
            // given
            String value = "TooMuchLooongPw!@";

            // when then
            assertThatThrownBy(() -> new Password(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(LENGTH_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 계정 비밀번호에 공백이 포함되면 예외가 발생한다.")
        @Test
        void createContainBlank() {
            // given
            String value = "have BlankPw!@";

            // when then
            assertThatThrownBy(() -> new Password(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(BLANK_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 계정 비밀번호는 영어+숫자+특수문자로 조합되어야한다. 이외의 경우는 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"onlyEnglish", "1234567", "!@#$%^&**", "engAnd123", "engAnd!@#", "1234!@#", "eng123!@한글포함"})
        void createMissMatchPattern(String value) {
            // when then
            assertThatThrownBy(() -> new Password(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PATTERN_EXCEPTION_MESSAGE);
        }
    }
}
