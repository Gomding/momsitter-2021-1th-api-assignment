package com.momsitter.domain;

import com.momsitter.domain.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.momsitter.domain.Email.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Email(회원 이메일 값 객체) 단위 테스트")
public class EmailTest {

    @DisplayName("Password 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("회원 이메일 객체 정상 생성된다.")
        @Test
        void create() {
            // given
            String value = "momsitter2022@gmail.com";

            // when
            Email email = new Email(value);

            // then
            assertThat(email).isEqualTo(new Email(value));
        }

        @DisplayName("회원 이메일이 null이면 예외가 발생한다.")
        @Test
        void createEmailIsNull() {
            // when then
            assertThatThrownBy(() -> new Email(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이메일 중 로컬 부분이 64자를 초과하면 예외가 발생한다.")
        @Test
        void createOverLocalLength() {
            // given
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < 65; i++) {
                value.append("a");
            }
            value.append("@gmail.com");

            // when then
            assertThatThrownBy(() -> new Email(value.toString()))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(LOCAL_LENGTH_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이메일 중 도메인 부분이 255자를 초과하면 예외가 발생한다.")
        @Test
        void createOverDomainLength() {
            // given
            StringBuilder value = new StringBuilder();
            value.append("a@");
            for (int i = 0; i < 256; i++) {
                value.append("g");
            }
            value.append(".com");

            // when then
            assertThatThrownBy(() -> new Email(value.toString()))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(DOMAIN_LENGTH_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이메일이 공백을 포함하면 예외가 발생한다.")
        @Test
        void createContainsBlank() {
            // given
            String value = "have blank@gmail.com";

            // when then
            assertThatThrownBy(() -> new Email(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(BLANK_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이메일이 이메일 포맷을 지키지 않은 형식이면 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"mom12gmail.com", "@gmail.com", "sitter12@gmail", "mom@sitter@gmail.com", "momsitter"})
        void createMissMatchPattern(String value) {
            // when then
            assertThatThrownBy(() -> new Email(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(PATTERN_EXCEPTION_MESSAGE);
        }
    }
}
