package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.momsitter.domain.SitterInfo.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("SitterInfo 단위 테스트")
public class SitterInfoTest {

    @DisplayName("SitterInfo 객체 생성 테스트")
    @Nested
    static class CreateTest {

        @DisplayName("SitterInfo 객체 정상적으로 생성한다.")
        @Test
        void create() {
            // given
            final int minAgeOfCaringChild = 3;
            final int maxAgeOfCaringChild = 5;
            final String aboutMe = "맘시터 과제 너무 즐거워요!";

            // when
            SitterInfo sitterInfo = new SitterInfo(minAgeOfCaringChild, maxAgeOfCaringChild, aboutMe);

            // then
            assertThat(sitterInfo.getMinAgeOfCaringChild()).isEqualTo(minAgeOfCaringChild);
            assertThat(sitterInfo.getMaxAgeOfCaringChild()).isEqualTo(maxAgeOfCaringChild);
            assertThat(sitterInfo.getAboutMe()).isEqualTo(aboutMe);
        }

        @DisplayName("SitterInfo 객체 생성 시 케어 가능한 최소 연령이 최대 연령보다 크면 예외가 발생한다.")
        @Test
        void createMinAgeBiggerThanMaxAge() {
            // given
            final int maxAgeOfCaringChild = 3;
            final int minAgeOfCaringChild = maxAgeOfCaringChild + 1;
            final String aboutMe = "맘시터 서비스를 케어하고 싶습니다!";

            // when
            assertThatThrownBy(() -> new SitterInfo(minAgeOfCaringChild, maxAgeOfCaringChild, aboutMe))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(INVALID_CARE_AGE_MESSAGE);
        }

        @DisplayName("SitterInfo 객체 생성 시 자기 소개가 null이면 예외가 발생한다.")
        @Test
        void createAboutMeIsNull() {
            // given
            final int minAgeOfCaringChild = 3;
            final int maxAgeOfCaringChild = 5;

            // when
            assertThatThrownBy(() -> new SitterInfo(minAgeOfCaringChild, maxAgeOfCaringChild, null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(ABOUT_ME_NULL_EXCEPTION_MESSAGE);
        }
    }
}
