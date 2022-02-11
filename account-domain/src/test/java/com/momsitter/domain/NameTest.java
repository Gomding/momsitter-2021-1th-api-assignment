package com.momsitter.domain;

import com.momsitter.exception.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.momsitter.domain.Name.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Name(회원 이름 값 객체) 단위 테스트")
public class NameTest {

    @DisplayName("Name 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("회원 이름 객체 정상 생성된다.")
        @Test
        void create() {
            // given
            final String value = "박시터부모";

            // when
            Name name = new Name(value);

            // then
            assertThat(name).isEqualTo(new Name(value));
        }

        @DisplayName("회원 이름이 null이면 예외가 발생한다.")
        @Test
        void createNameIsNull() {
            // when then
            assertThatThrownBy(() -> new Name(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이름에 공백이 포함되면 예외가 발생한다.")
        @Test
        void createContainsBlank() {
            // given
            final String value = "박시터 박부모";

            // when then
            assertThatThrownBy(() -> new Name(value))
                    .isExactlyInstanceOf(InvalidArgumentException.class)
                    .hasMessage(BLANK_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 이름에 특수문자가 포함되면 예외가 발생한다.")
        @Test
        void createNestedSpecialCharacter() {
            // given
            final String value = "맘편한세상화이팅!@";

            // when then
            assertThatThrownBy(() -> new Name(value))
                    .isExactlyInstanceOf(InvalidArgumentException.class)
                    .hasMessage(INVALID_NAME_CHARACTER_EXCEPTION);
        }
    }
}
