package com.momsitter.domain;

import com.momsitter.domain.DateOfBirth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static com.momsitter.domain.DateOfBirth.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DateOfBirth(회원 생년월일 값 객체) 단위 테스트")
public class DateOfBirthTest {

    @DisplayName("DateOfBirth 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("회원 생년월일 객체 정상 생성된다.")
        @Test
        void create() {
            // given
            LocalDate value = LocalDate.of(1992, 5, 30);

            // when
            DateOfBirth dateOfBirth = new DateOfBirth(value);

            // then
            assertThat(dateOfBirth).isEqualTo(new DateOfBirth(value));
        }

        @DisplayName("회원 생년월일이 null이면 예외가 발생한다.")
        @Test
        void createDateOfBirthIsNull() {
            // when then
            assertThatThrownBy(() -> new DateOfBirth(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 생년월일이 현재로부터 이후의 년월일이라면 예외가 발생한다.")
        @Test
        void createAfterLocalDate() {
            // given
            LocalDate value = LocalDate.now().plusDays(1L);

            // when then
            assertThatThrownBy(() -> new DateOfBirth(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(AFTER_DATE_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 생년월일 객체를 year,month,dayOfMonth를 인자로 생성한다.")
        @Test
        void ofYearMonthAndDayOfMonth() {
            // given
            int year = 1992;
            int month = 5;
            int dayOfMonth = 30;

            // when
            DateOfBirth dateOfBirth = DateOfBirth.of(year, month, dayOfMonth);

            // then
            assertThat(dateOfBirth).isEqualTo(DateOfBirth.of(year, month, dayOfMonth));
        }

        @DisplayName("회원 생년원일 year,month,dayOfMonth 인자로 객체 생성시 유효한 월(1-12),일(1-31)이 아니라면 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"1992,0,30", "1992,5,0", "1992,2,31"})
        void ofInvalidYearMonthAndDayOfMonth(int year, int month, int dayOfMonth) {
            // when then
            assertThatThrownBy(() -> DateOfBirth.of(year, month, dayOfMonth))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_DATE_TIME_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 생년월일 객체를 문자열 인자로 생성한다.")
        @Test
        void ofString() {
            // given
            String value = "19920530";

            // when
            DateOfBirth dateOfBirth = DateOfBirth.of(value);

            // then
            assertThat(dateOfBirth).isEqualTo(DateOfBirth.of(value));
        }

        @DisplayName("회원 생년월일 객체를 문자열 인자로 생성할 때 null을 넘기면 예외가 발생한다.")
        @Test
        void ofStringIsNull() {
            // when then
            assertThatThrownBy(() -> DateOfBirth.of(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("회원 생년원일 객체를 문자열 인자로 생성할 때 유효한 월(1-12),일(1-31)이 아니라면 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"19920030", "19920500", "1992053", "19921301", "19920532"})
        void ofInvalidString(String value) {
            // when then
            assertThatThrownBy(() -> DateOfBirth.of(value))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_DATE_TIME_EXCEPTION_MESSAGE);
        }
    }
}
