package com.momsitter.service;

import com.momsitter.domain.Account;
import com.momsitter.exception.DuplicateException;
import com.momsitter.exception.InvalidArgumentException;
import com.momsitter.exception.InvalidStateException;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.dto.account.AccountCreateRequest;
import com.momsitter.ui.dto.account.AccountInfoResponse;
import com.momsitter.ui.dto.account.AccountResponse;
import com.momsitter.ui.dto.account.AccountUpdateRequest;
import com.momsitter.ui.dto.account.parent.*;
import com.momsitter.ui.dto.account.sitter.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    }

    @DisplayName("회원의 내 정보 수정 테스트")
    @Nested
    class UpdateAccountInfoTest {

        @DisplayName("회원의 내 정보 수정이 정상적으로 된다.")
        @Test
        void updateAccountInfo() {
            // given
            AccountResponse createdResponse = createSitterAccount().getAccount();
            AccountUpdateRequest updateRequest = new AccountUpdateRequest("여", "updatepw123!", "update@test.com");

            // when
            accountService.updateAccountInfo(createdResponse.getId(), updateRequest);
            Account account = accountRepository.findById(createdResponse.getId()).get();

            // then
            assertThat(account.getGender().getGenderName()).isNotEqualTo(createdResponse.getGender());
            assertThat(account.getPassword().getValue()).isEqualTo("updatepw123!"); // 응답 객체에는 비밀번호가 없어서 문자열로 대체
            assertThat(account.getEmail().getValue()).isNotEqualTo(createdResponse.getEmail());
        }

        @DisplayName("수정 요청한 이메일이 이미 존재하는 이메일이라면 예외가 발생한다.(자신의 현재 이메일 같은 경우는 정상동작한다)")
        @Test
        void updateAccountInfoDuplicateEmail() {
            // given
            SitterCreateResponse createResponse = createSitterAccount();
            ParentCreateResponse createResponse2 = createParentAccount();
            AccountUpdateRequest updateRequest = new AccountUpdateRequest("여", "updatepw123!",
                    createResponse2.getAccount().getEmail());

            // when then
            assertThatThrownBy(() -> accountService.updateAccountInfo(createResponse.getAccount().getId(), updateRequest))
                    .isExactlyInstanceOf(DuplicateException.class)
                    .hasMessage("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
            // 수정하는 회원의 email과 수정 요청한 email이 같으면 예외는 발생하지 않는다.
            assertDoesNotThrow(() -> accountService.updateAccountInfo(createResponse2.getAccount().getId(), updateRequest));
        }
    }

    @DisplayName("시터 정보 수정 테스트")
    @Nested
    class UpdateSitterInfoTest {

        @DisplayName("시터 정보 수정이 정상적으로 된다.")
        @Test
        void updateSitterInfo() {
            // given
            SitterCreateResponse createResponse = createSitterAccount();
            SitterInfoResponse sitterInfo = createResponse.getSitterInfo();
            SitterUpdateRequest updateRequest = new SitterUpdateRequest(
                    sitterInfo.getMinCareAge() + 1,
                    sitterInfo.getMaxCareAge() + 1,
                    "더 잘한다는 걸 어필하기 위한 수정"
            );

            // when
            SitterInfoResponse updateResponse = accountService.updateSitterInfo(createResponse.getAccount().getId(), updateRequest);

            // then
            assertThat(updateResponse.getMinCareAge()).isNotEqualTo(sitterInfo.getMinCareAge());
            assertThat(updateResponse.getMaxCareAge()).isNotEqualTo(sitterInfo.getMaxCareAge());
            assertThat(updateResponse.getAboutMe()).isNotEqualTo(sitterInfo.getAboutMe());
        }

        @DisplayName("시터 회원으로 등록되지 않은 회원은 시터 정보 수정 시 예외가 발생한다.")
        @Test
        void updateSitterInfoWithParentAccount() {
            // given
            ParentCreateResponse createResponse = createParentAccount();
            SitterUpdateRequest updateRequest = new SitterUpdateRequest(4, 6, "자기소개 수정");

            // when
            assertThatThrownBy(() -> accountService.updateSitterInfo(createResponse.getAccount().getId(), updateRequest))
                    .isExactlyInstanceOf(InvalidStateException.class)
                    .hasMessage("시터회원으로 등록하지 않으면 시터회원 정보를 수정할 수 없습니다.");
        }
    }

    @DisplayName("부모 정보 수정 테스트")
    @Nested
    class UpdateParentInfoTest {

        @DisplayName("부모 정보 수정이 정상적으로 된다.")
        @Test
        void updateParentInfo() {
            // given
            ParentCreateResponse createResponse = createParentAccount();
            ParentInfoResponse parentInfo = createResponse.getParentInfo();
            List<ChildUpdateRequest> childUpdateRequests = parentInfo.getChildren().stream()
                    .map(c -> new ChildUpdateRequest(c.getId(), c.getDateOfBirth().plusDays(1L), c.getGender()))
                    .collect(Collectors.toList());
            ParentUpdateRequest updateRequest = new ParentUpdateRequest(childUpdateRequests, "맘시터 서비스 너무 좋아요!");

            // when
            ParentInfoResponse updateResponse = accountService.updateParentInfo(createResponse.getAccount().getId(), updateRequest);

            // then
            assertThat(updateResponse.getId()).isEqualTo(parentInfo.getId());
            assertThat(updateResponse.getChildren()).hasSize(parentInfo.getChildren().size());
            assertThat(updateResponse.getCareRequestInfo()).isNotEqualTo(parentInfo.getCareRequestInfo());
        }

        @DisplayName("시터 회원으로 등록되지 않은 회원은 시터 정보 수정 시 예외가 발생한다.")
        @Test
        void updateParentInfoWithSitterAccount() {
            // given
            SitterCreateResponse createResponse = createSitterAccount();
            List<ChildUpdateRequest> childUpdateRequests = Collections.emptyList();
            ParentUpdateRequest updateRequest = new ParentUpdateRequest(childUpdateRequests, "맘시터 서비스 너무 좋아요!");

            // when then
            assertThatThrownBy(() -> accountService.updateParentInfo(createResponse.getAccount().getId(), updateRequest))
                    .isExactlyInstanceOf(InvalidStateException.class)
                    .hasMessage("부모회원으로 등록하지 않으면 부모회원 정보를 수정할 수 없습니다.");
        }

        @DisplayName("부모 정보 수정 시 등록된 아이의 고유 번호가 수정하려는 아이의 고유 번호와 일치하지 않으면 예외가 발생한다.")
        @Test
        void updateParentInfoDifferentChild() {
            // given
            ParentCreateResponse createResponse = createParentAccount();
            ParentInfoResponse parentInfo = createResponse.getParentInfo();
            List<ChildUpdateRequest> childUpdateRequests = parentInfo.getChildren().stream()
                    .map(c -> new ChildUpdateRequest(c.getId() + 1L, c.getDateOfBirth().plusDays(1L), c.getGender()))
                    .collect(Collectors.toList());
            ParentUpdateRequest updateRequest = new ParentUpdateRequest(childUpdateRequests, "맘시터 서비스 너무 좋아요!");

            // when then
            assertThatThrownBy(() -> accountService.updateParentInfo(createResponse.getAccount().getId(), updateRequest))
                    .isExactlyInstanceOf(InvalidArgumentException.class)
                    .hasMessage("등록된 아이들과 수정을 원하는 아이들이 일치하지 않습니다.");
        }
    }

    @DisplayName("시터회원에 부모회원 정보 추가 테스트")
    @Nested
    class AddActivityParentTest {

        @DisplayName("시터회원이 부모회원으로도 활동할 수 있다.")
        @Test
        void addActivityParent() {
            // given
            SitterCreateResponse createResponse = createSitterAccount();
            ParentInfoRequest parentInfoRequest = new ParentInfoRequest(
                    Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                            new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                    "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
            );

            // when
            ParentInfoResponse response = accountService.addActivityParent(createResponse.getAccount().getId(), parentInfoRequest);

            // then
            assertThat(response.getId()).isNotNull();
            assertThat(response.getChildren()).extracting("id").doesNotContainNull();
            assertThat(response.getCareRequestInfo()).isEqualTo("매일 2시간정도 아이를 봐주실 시터님 구해요:)");
        }

        @DisplayName("이미 부모회원으로 활동중인 회원에 부모 정보를 추가하려면 예외가 발생한다.")
        @Test
        void addActivityParentWithParentAccount() {
            // given
            ParentCreateResponse createResponse = createParentAccount();
            ParentInfoRequest parentInfoRequest = new ParentInfoRequest(
                    Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                            new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                    "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
            );

            // when then
            assertThatThrownBy(() -> accountService.addActivityParent(createResponse.getAccount().getId(), parentInfoRequest))
                    .isExactlyInstanceOf(InvalidStateException.class)
                    .hasMessage("이미 부모회원으로 활동중입니다.");
        }
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
                new AccountCreateRequest("찰리박",
                        LocalDate.of(1992, 5, 30),
                        "남",
                        "park1992",
                        "password12!@",
                        "park@test.com"),
                new ParentInfoRequest(
                        Arrays.asList(new ChildRequest(LocalDate.of(2020, 5, 30), "남"),
                                new ChildRequest(LocalDate.of(2018, 3, 25), "여")),
                        "매일 2시간정도 아이를 봐주실 시터님 구해요:)"
                )
        );
        return accountService.createParentAccount(request);
    }
}
