package com.momsitter.service;

import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.account.dto.SitterCreateRequest;
import com.momsitter.ui.account.dto.SitterCreateResponse;
import com.momsitter.ui.account.dto.SitterInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AccountService 통합 테스트")
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @DisplayName("시터 회원가입을 테스트")
    @Nested
    class SitterAccountCreateTest {

        @DisplayName("시터 회원가입이 정상적으로 된다.")
        @Test
        void createSitterAccount() {
            // given
            SitterCreateRequest request = new SitterCreateRequest(
                    "박민영",
                    LocalDate.of(1992, 5, 30),
                    "남",
                    "charlie123",
                    "password12!@",
                    "test@test.com",
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );

            // when
            SitterCreateResponse response = accountService.createSitterAccount(request);

            // then
            assertThat(response.getId()).isNotNull();
            assertThat(response.getName()).isEqualTo("박민영");
            assertThat(response.getDateOfBirth()).isEqualTo(LocalDate.of(1992, 5, 30));
            assertThat(response.getGender()).isEqualTo("남");
            assertThat(response.getAccountId()).isEqualTo("charlie123");
            assertThat(response.getPassword()).isEqualTo("password12!@");
            assertThat(response.getEmail()).isEqualTo("test@test.com");
            assertThat(response.getSitterInfo().getMinCareAge()).isEqualTo(3);
            assertThat(response.getSitterInfo().getMaxCareAge()).isEqualTo(6);
            assertThat(response.getSitterInfo().getAboutMe()).isEqualTo("아이들을 좋아하고, 잘 돌봐유");
        }

        @DisplayName("시터 회원가입 시 계정ID가 이미 존재한다면 예외가 발생한다.")
        @Test
        void createSitterAccountDuplicateAccountId() {
            // given
            SitterCreateRequest request = new SitterCreateRequest(
                    "박민영",
                    LocalDate.of(1992, 5, 30),
                    "남",
                    "charlie123",
                    "password12!@",
                    "test@test.com",
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );
            accountService.createSitterAccount(request);

            SitterCreateRequest duplicateAccountIdRequest = new SitterCreateRequest(
                    "박찰리",
                    LocalDate.of(1980, 2, 12),
                    "여",
                    request.getAccountId(),
                    "drowssap12!@",
                    "email@email.com",
                    new SitterInfoRequest(1, 4, "신속완벽 돌봄서비스 제공")
            );

            // when then
            assertThatThrownBy(() -> accountService.createSitterAccount(duplicateAccountIdRequest))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
        }

        @DisplayName("시터 회원가입 시 입력한 이메일을 사용하는 계정이 이미 존재한다면 예외가 발생한다.")
        @Test
        void createSitterAccountDuplicateEmail() {
            // given
            SitterCreateRequest request = new SitterCreateRequest(
                    "박민영",
                    LocalDate.of(1992, 5, 30),
                    "남",
                    "charlie123",
                    "password12!@",
                    "test@test.com",
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );
            accountService.createSitterAccount(request);

            SitterCreateRequest duplicateEmailRequest = new SitterCreateRequest(
                    "박찰리",
                    LocalDate.of(1980, 2, 12),
                    "여",
                    "parkchal1980",
                    "drowssap12!@",
                    request.getEmail(),
                    new SitterInfoRequest(1, 4, "신속완벽 돌봄서비스 제공")
            );

            // when then
            assertThatThrownBy(() -> accountService.createSitterAccount(duplicateEmailRequest))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
        }
    }
}
