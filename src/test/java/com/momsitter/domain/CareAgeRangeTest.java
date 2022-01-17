package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.momsitter.domain.CareAgeRange.INVALID_CARE_AGE_MESSAGE;
import static com.momsitter.domain.CareAgeRange.NEGATIVE_CARE_AGE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("시터회원의 케어 가능한 연령범위를 의미하는 CareAgeRange 단위 테스트")
public class CareAgeRangeTest {

    @DisplayName("CareAgeRange 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("케어 가능한 연령범위 객체가 정상적으로 생성된다.")
        @Test
        void create() {
            // given
            final int minAge = 3;
            final int maxAge = 5;

            // when
            CareAgeRange careAgeRange = new CareAgeRange(minAge, maxAge);

            // then
            assertThat(careAgeRange).isEqualTo(new CareAgeRange(minAge, maxAge));
        }

        @DisplayName("케어 가능한 연령이 0보다 작으면 예외가 발생한다.")
        @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
        @CsvSource({"-1, 0", "0, -1"})
        void createMinAgeBiggerThanMaxAge(int minAge, int maxAge) {
            // when
            assertThatThrownBy(() -> new CareAgeRange(minAge, maxAge))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(NEGATIVE_CARE_AGE_MESSAGE);
        }

        @DisplayName("케어 가능한 최소 연령이 최대 연령보다 크다면 예외가 발생한다.")
        @Test
        void createMinAgeBiggerThanMaxAge() {
            // given
            final int maxAge = 5;
            final int minAge = maxAge + 1;

            // when
            assertThatThrownBy(() -> new CareAgeRange(minAge, maxAge))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_CARE_AGE_MESSAGE);
        }
    }
}
