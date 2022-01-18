package com.momsitter.service;

import com.momsitter.exception.DuplicateException;
import com.momsitter.exception.InvalidArgumentException;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.dto.account.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

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
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );

            // when
            SitterCreateResponse response = accountService.createSitterAccount(request);

            // then
            AccountResponse accountResponse = response.getAccount();
            assertThat(accountResponse.getId()).isNotNull();
            assertThat(accountResponse.getName()).isEqualTo("박민영");
            assertThat(accountResponse.getDateOfBirth()).isEqualTo(LocalDate.of(1992, 5, 30));
            assertThat(accountResponse.getGender()).isEqualTo("남");
            assertThat(accountResponse.getAccountId()).isEqualTo("charlie123");
            assertThat(accountResponse.getEmail()).isEqualTo("test@test.com");

            SitterInfoResponse sitterInfoResponse = response.getSitterInfo();
            assertThat(sitterInfoResponse.getId()).isNotNull();
            assertThat(sitterInfoResponse.getMinCareAge()).isEqualTo(3);
            assertThat(sitterInfoResponse.getMaxCareAge()).isEqualTo(6);
            assertThat(sitterInfoResponse.getAboutMe()).isEqualTo("아이들을 좋아하고, 잘 돌봐유");
        }

        @DisplayName("시터 회원가입 시 계정ID가 이미 존재한다면 예외가 발생한다.")
        @Test
        void createSitterAccountDuplicateAccountId() {
            // given
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );
            accountService.createSitterAccount(request);

            SitterCreateRequest duplicateAccountIdRequest = new SitterCreateRequest(
                    new AccountCreateRequest("박찰리",
                            LocalDate.of(1980, 2, 12),
                            "여",
                            request.getAccount().getAccountId(),
                            "drowssap12!@",
                            "email@email.com"),
                    new SitterInfoRequest(1, 4, "신속완벽 돌봄서비스 제공")
            );

            // when then
            assertThatThrownBy(() -> accountService.createSitterAccount(duplicateAccountIdRequest))
                    .isExactlyInstanceOf(DuplicateException.class)
                    .hasMessage("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
        }

        @DisplayName("시터 회원가입 시 입력한 이메일을 사용하는 계정이 이미 존재한다면 예외가 발생한다.")
        @Test
        void createSitterAccountDuplicateEmail() {
            // given
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );
            accountService.createSitterAccount(request);

            SitterCreateRequest duplicateEmailRequest = new SitterCreateRequest(
                    new AccountCreateRequest("박찰리",
                            LocalDate.of(1980, 2, 12),
                            "여",
                            "parkchal1980",
                            "drowssap12!@",
                            request.getAccount().getEmail()),
                    new SitterInfoRequest(1, 4, "신속완벽 돌봄서비스 제공")
            );

            // when then
            assertThatThrownBy(() -> accountService.createSitterAccount(duplicateEmailRequest))
                    .isExactlyInstanceOf(DuplicateException.class)
                    .hasMessage("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
        }
    }

    @DisplayName("부모 회원가입을 테스트")
    @Nested
    class ParentAccountCreateTest {

        @DisplayName("부모 회원가입이 정상적으로 된다.")
        @Test
        void createParentAccount() {
            // given
            ParentCreateRequest request = new ParentCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );

            // when
            ParentCreateResponse response = accountService.createParentAccount(request);

            // then
            AccountResponse accountResponse = response.getAccount();
            assertThat(accountResponse.getId()).isNotNull();
            assertThat(accountResponse.getName()).isEqualTo("박민영");
            assertThat(accountResponse.getDateOfBirth()).isEqualTo(LocalDate.of(1992, 5, 30));
            assertThat(accountResponse.getGender()).isEqualTo("남");
            assertThat(accountResponse.getAccountId()).isEqualTo("charlie123");
            assertThat(accountResponse.getEmail()).isEqualTo("test@test.com");

            ParentInfoResponse parentInfoResponse = response.getParentInfo();
            assertThat(parentInfoResponse.getId()).isNotNull();
            assertThat(parentInfoResponse.getChildren()).extracting("id").doesNotContainNull();
            assertThat(parentInfoResponse.getChildren()).extracting("dateOfBirth").containsExactly(LocalDate.of(2020, 5, 30), LocalDate.of(2018, 3, 25));
            assertThat(parentInfoResponse.getChildren()).extracting("gender").containsExactly("남", "여");
            assertThat(parentInfoResponse.getCareRequestInfo()).isEqualTo("매일 2시간정도 아이를 봐주실 시터님 구해요:)");
        }

        @DisplayName("부모 회원가입 시 계정ID가 이미 존재한다면 예외가 발생한다.")
        @Test
        void createParentAccountDuplicateAccountId() {
            // given
            ParentCreateRequest request = new ParentCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );
            accountService.createParentAccount(request);

            ParentCreateRequest newRequest = new ParentCreateRequest(
                    new AccountCreateRequest("박찰리",
                            LocalDate.of(1988, 3, 23),
                            "여",
                            request.getAccount().getAccountId(),
                            "drowssap88#$",
                            "email@email.com"),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );
            // when then
            assertThatThrownBy(() -> accountService.createParentAccount(newRequest))
                    .isExactlyInstanceOf(DuplicateException.class)
                    .hasMessage("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
        }

        @DisplayName("부모 회원가입 시 입력한 이메일을 사용하는 계정이 이미 존재한다면 예외가 발생한다.")
        @Test
        void createParentAccountDuplicateEmail() {
            // given
            ParentCreateRequest request = new ParentCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );
            accountService.createParentAccount(request);

            ParentCreateRequest newRequest = new ParentCreateRequest(
                    new AccountCreateRequest("박찰리",
                            LocalDate.of(1988, 3, 23),
                            "여",
                            "parkchal88",
                            "drowssap88#$",
                            request.getAccount().getEmail()),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );
            // when
            assertThatThrownBy(() -> accountService.createParentAccount(newRequest))
                    .isExactlyInstanceOf(DuplicateException.class)
                    .hasMessage("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
        }
    }

    @DisplayName("회원 번호로 회원 정보 조회 테스트")
    @Nested
    class FindAccountTest {

        @DisplayName("시터로만 등록한 회원 정보를 조회하면 시터 정보만 조회된다.")
        @Test
        void findSitterAccountById() {
            // given
            SitterCreateResponse createResponse = createSitterAccount();

            // when
            AccountInfoResponse response = accountService.findAccountById(createResponse.getAccount().getId());

            // then
            assertThat(response.getSitter()).isNotNull();
            assertThat(response.getSitter().getId()).isNotNull();
            assertThat(response.getParent()).isNull();
        }

        @DisplayName("부모로만 등록한 회원 정보를 조회하면 부모 정보만 조회된다.")
        @Test
        void findParentAccountById() {
            // given
            ParentCreateResponse createResponse = createParentAccount();

            // when
            AccountInfoResponse response = accountService.findAccountById(createResponse.getAccount().getId());

            // then
            assertThat(response.getParent()).isNotNull();
            assertThat(response.getParent().getId()).isNotNull();
            assertThat(response.getSitter()).isNull();
        }

        @DisplayName("존재하지 않는 회원 번호로 조회하면 예외가 발생한다.")
        @Test
        void findAccountByNonExistsId() {
            // when then
            assertThatThrownBy(() -> accountService.findAccountById(Long.MAX_VALUE))
                    .isExactlyInstanceOf(InvalidArgumentException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        private SitterCreateResponse createSitterAccount() {
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new SitterInfoRequest(3, 6, "아이들을 좋아하고, 잘 돌봐유")
            );
            return accountService.createSitterAccount(request);
        }

        private ParentCreateResponse createParentAccount() {
            ParentCreateRequest request = new ParentCreateRequest(
                    new AccountCreateRequest("박민영",
                            LocalDate.of(1992, 5, 30),
                            "남",
                            "charlie123",
                            "password12!@",
                            "test@test.com"),
                    new ParentInfoRequest(
                            Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                    new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                            "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                    )
            );
            return accountService.createParentAccount(request);
        }
    }
}
