package com.momsitter.domain;

import com.momsitter.domain.CareAgeRange;
import com.momsitter.domain.SitterInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.momsitter.domain.SitterInfo.ABOUT_ME_NULL_EXCEPTION_MESSAGE;
import static com.momsitter.domain.SitterInfo.CARE_AGE_RANGE_NULL_EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("SitterInfo 단위 테스트")
public class SitterInfoTest {

    @DisplayName("SitterInfo 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("SitterInfo 객체 정상적으로 생성한다.")
        @Test
        void create() {
            // given
            final CareAgeRange careAgeRange = new CareAgeRange(3, 5);
            final String aboutMe = "맘시터 과제 너무 즐거워요!";

            // when
            SitterInfo sitterInfo = new SitterInfo(careAgeRange, aboutMe);

            // then
            assertThat(sitterInfo.getCareAgeRange()).isEqualTo(careAgeRange);
            assertThat(sitterInfo.getAboutMe()).isEqualTo(aboutMe);
        }

        @DisplayName("SitterInfo 객체 생성 시 케어 가능 연령범위가 null이면 예외가 발생한다.")
        @Test
        void createCareAgeRangeIsNull() {
            // given
            final String aboutMe = "아이와 코딩 공부를 함께할 수 있습니다.";

            // when then
            assertThatThrownBy(() -> new SitterInfo(null, aboutMe))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(CARE_AGE_RANGE_NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("SitterInfo 객체 생성 시 자기 소개가 null이면 예외가 발생한다.")
        @Test
        void createAboutMeIsNull() {
            // given
            CareAgeRange careAgeRange = new CareAgeRange(3, 5);

            // when then
            assertThatThrownBy(() -> new SitterInfo(careAgeRange, null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(ABOUT_ME_NULL_EXCEPTION_MESSAGE);
        }
    }
}
