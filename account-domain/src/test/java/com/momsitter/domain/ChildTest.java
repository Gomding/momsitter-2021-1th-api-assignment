package com.momsitter.domain;

import com.momsitter.domain.Child;
import com.momsitter.domain.DateOfBirth;
import com.momsitter.domain.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Child 단위 테스트")
class ChildTest {

    @DisplayName("Child 객체 정상적으로 생성된다.")
    @Test
    void create() {
        // given
        DateOfBirth dateOfBirth = DateOfBirth.of("20201030");
        Gender gender = Gender.MALE;

        // when
        Child child = new Child(dateOfBirth, gender);

        // then
        assertThat(child.getDateOfBirth()).isEqualTo(DateOfBirth.of("20201030"));
        assertThat(child.getGender()).isEqualTo(Gender.MALE);
    }
}
