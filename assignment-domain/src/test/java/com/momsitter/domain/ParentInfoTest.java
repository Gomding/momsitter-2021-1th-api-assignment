package com.momsitter.domain;

import com.momsitter.domain.Child;
import com.momsitter.domain.DateOfBirth;
import com.momsitter.domain.Gender;
import com.momsitter.domain.ParentInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ParentInfo 단위 테스트")
public class ParentInfoTest {

    @DisplayName("ParentInfo 객체 생성 테스트")
    @Nested
    class CreateTest {

        @DisplayName("ParentInfo 객체 정상적으로 생성한다.")
        @Test
        void create() {
            // given
            List<Child> children = Arrays.asList(
                    new Child(1L, DateOfBirth.of("19920530"), Gender.MALE),
                    new Child(2L, DateOfBirth.of("20201030"), Gender.FEMALE)
            );
            String careRequest = "하루종일 코딩할 수 있게 해줄 시터를 찾습니다.";

            // when
            ParentInfo parentInfo = new ParentInfo(1L, children, careRequest);

            // then
            assertThat(parentInfo.getId()).isEqualTo(1L);
            assertThat(parentInfo.getChildren()).containsExactly(
                    new Child(1L, DateOfBirth.of("19920530"), Gender.MALE),
                    new Child(2L, DateOfBirth.of("20201030"), Gender.FEMALE)
            );
            assertThat(parentInfo.getCareRequestInfo()).isEqualTo(careRequest);
        }

        @DisplayName("돌봄 아이들 정보가 null이라면 예외가 발생한다.")
        @Test
        void createChildrenInfoIsNull() {
            // given
            String careRequest = "돌봄 신청할 아이가 없어서 null을 입력헀어요!";

            // when then
            assertThatThrownBy(() -> new ParentInfo(1L, null, careRequest))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(ParentInfo.CHILDREN_NULL_EXCEPTION_MESSAGE);
        }

        @DisplayName("돌봄 신청 내용(careRequestInfo)가 null이라면 예외가 발생한다.")
        @Test
        void createCareRequestInfoIsNull() {
            // given
            List<Child> children = Arrays.asList(
                    new Child(1L, DateOfBirth.of("19920530"), Gender.MALE),
                    new Child(2L, DateOfBirth.of("20201030"), Gender.FEMALE)
            );

            // when then
            assertThatThrownBy(() -> new ParentInfo(1L, children, null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage(ParentInfo.CARE_REQUEST_INFO_NULL_EXCEPTION_MESSAGE);
        }
    }
}
