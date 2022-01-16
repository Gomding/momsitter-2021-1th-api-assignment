package com.momsitter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @DisplayName("유저 객체 정상적으로 생성한다.")
    @Test
    void create() {
        // given
        String name = "이름";
        LocalDate dateOfBirth = LocalDate.parse("19920530", DateTimeFormatter.ofPattern("yyyyMMdd"));
        Gender gender = Gender.MALE;
        String accountId = "momsitter2022";
        String password = "momsitterpw!@";
        String email = "momsitter@gmail.com";

        // when
        User user = new User.Builder()
                .name(name)
                .dateOfBirth(dateOfBirth)
                .gender(gender)
                .accountId(accountId)
                .password(password)
                .email(email)
                .build();

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(user.getGender()).isEqualTo(gender);
        assertThat(user.getAccountId()).isEqualTo(accountId);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getEmail()).isEqualTo(email);
    }
}
