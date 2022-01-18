package com.momsitter.service;

import com.momsitter.domain.*;
import com.momsitter.exception.LoginFailException;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.dto.auth.TokenRequest;
import com.momsitter.ui.dto.auth.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AuthService 통합 테스트")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    AccountRepository accountRepository;

    @DisplayName("로그인 테스트")
    @Nested
    class LoginTest {

        @DisplayName("로그인이 정상적으로 완료되면 인증토큰을 반환한다.")
        @Test
        void login() {
            // given
            String accountId = "parksitter12";
            String password = "park1234!@";
            createAccount(accountId, password);

            // when
            TokenResponse tokenResponse = authService.login(new TokenRequest(accountId, password));

            // then
            assertThat(tokenResponse.getValue()).isNotNull();
        }

        @DisplayName("로그인 시 제출한 아이디가 존재하지 않으면 예외가 발생한다.")
        @Test
        void loginNotExistsAccountId() {
            // given
            String accountId = "parksitter12";
            String password = "park1234!@";

            // when then
            assertThatThrownBy(() -> authService.login(new TokenRequest(accountId, password)))
                    .isExactlyInstanceOf(LoginFailException.class)
                    .hasMessage("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        }

        @DisplayName("로그인 시 제출한 아이디에 비밀번호가 일치하지 않으면 예외가 발생한다.")
        @Test
        void loginInvalidPassword() {
            // given
            String accountId = "parksitter12";
            String password = "park1234!@";
            createAccount(accountId, password);

            // when then
            assertThatThrownBy(() -> authService.login(new TokenRequest(accountId, "failpw123!")))
                    .isExactlyInstanceOf(LoginFailException.class)
                    .hasMessage("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        }
    }

    public void createAccount(String accountId, String password) {
        Account account = new Account.Builder()
                .name(new Name("찰리"))
                .dateOfBirth(DateOfBirth.of("19920530"))
                .gender(Gender.MALE)
                .accountId(new AccountId(accountId))
                .password(new Password(password))
                .email(new Email("test@test.com"))
                .build();
        SitterInfo sitterInfo = new SitterInfo(new CareAgeRange(3, 6), "돌봄왕");
        account.registerSitter(sitterInfo);
        accountRepository.save(account);
    }
}
