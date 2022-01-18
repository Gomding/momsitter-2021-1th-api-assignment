package com.momsitter.acceptance;

import com.momsitter.domain.Name;
import com.momsitter.exception.ExceptionResponse;
import com.momsitter.ui.account.dto.*;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 관련 인수 테스트")
class AccountAcceptanceTest extends AcceptanceTest {

    public static final String NAME = "박민영";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1992, 5, 30);
    public static final String GENDER = "남";
    public static final String ACCOUNT_ID = "parkSitter92";
    public static final String PASSWORD = "park1234!@";
    public static final String EMAIL = "test@test.com";

    public static final int SITTER_MIN_CARE_AGE = 3;
    public static final int SITTER_MAX_CARE_AGE = 5;
    public static final String ABOUT_ME = "아이들의 가장 가까운 친구, 돌보는걸 너무 잘해요";

    public static final LocalDate CHILD_DATE_OF_BIRTH1 = LocalDate.of(2020, 5, 15);
    public static final String CHILD_GENDER1 = "남";
    public static final LocalDate CHILD_DATE_OF_BIRTH2 = LocalDate.of(2018, 3, 30);
    public static final String CHILD_GENDER2 = "여";
    public static final String PARENT_CARE_REQUEST_INFO = "잘 돌봐주실분!";

    @BeforeEach
    void setUpOnAccountAcceptance() {
        super.setUp();
    }

    @AfterEach
    void clearOnAccountAcceptance() {
        super.clear();
    }

    @DisplayName("시터 회원의 정보를 관리한다.")
    @Test
    void manageSitterAccount() {
        SitterCreateRequest request = 시터_회원_가입_요청을_생성한다();
        ExtractableResponse<Response> createResponse = 시터_회원_가입을_요청(request);
        시터_회원_생성됨(createResponse);
    }

    @DisplayName("부모 회원의 정보를 관리한다.")
    @Test
    void manageParentAccount() {
        ParentCreateRequest request = 부모_회원_가입_요청을_생성한다();
        ExtractableResponse<Response> createResponse = 부모_회원_가입을_요청(request);
        부모_회원_생성됨(createResponse);
    }

    private SitterCreateRequest 시터_회원_가입_요청을_생성한다() {
        return new SitterCreateRequest(
                new AccountCreateRequest(NAME, DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                new SitterInfoRequest(SITTER_MIN_CARE_AGE, SITTER_MAX_CARE_AGE, ABOUT_ME)
        );
    }

    private ExtractableResponse<Response> 시터_회원_가입을_요청(SitterCreateRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/accounts/sitter")
                .then().log().all()
                .extract();
    }

    private void 시터_회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private ParentCreateRequest 부모_회원_가입_요청을_생성한다() {
        ChildRequest childRequest1 = new ChildRequest(CHILD_DATE_OF_BIRTH1, CHILD_GENDER1);
        ChildRequest childRequest2 = new ChildRequest(CHILD_DATE_OF_BIRTH2, CHILD_GENDER2);
        return new ParentCreateRequest(
                new AccountCreateRequest(NAME, DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                new ParentInfoRequest(Arrays.asList(childRequest1, childRequest2), PARENT_CARE_REQUEST_INFO)
        );
    }

    private ExtractableResponse<Response> 부모_회원_가입을_요청(ParentCreateRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/accounts/parent")
                .then().log().all()
                .extract();
    }

    private void 부모_회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("[공통]회원가입 잘못된 요청 테스트")
    @Nested
    class CreateSitterAccountTest {
        @DisplayName("POST /api/accounts/sitter -> 이미 존재하는 계정ID로 시터 회원가입을 요청하면 실패한다.")
        @Test
        void createSitterAccountDuplicateAccountId() {
            //given
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest(NAME, DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                    new SitterInfoRequest(SITTER_MIN_CARE_AGE, SITTER_MAX_CARE_AGE, ABOUT_ME)
            );
            시터_회원가입을_요청(request);
            SitterCreateRequest 이미_존재하는_계정ID를_가진_시터_회웝가입_요청 = new SitterCreateRequest(
                    new AccountCreateRequest("박시터",
                            LocalDate.of(1988, 3, 30),
                            "여",
                            ACCOUNT_ID,
                            "password!@@12",
                            "gmail@gmail.com"),
                    new SitterInfoRequest(1, 3, "베테랑 시터입니다.")
            );

            // when
            ExtractableResponse<Response> response = 시터_회원가입을_요청(이미_존재하는_계정ID를_가진_시터_회웝가입_요청);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
            ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
            assertThat(exceptionResponse.getMessage()).isEqualTo("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
        }

        @DisplayName("POST /api/accounts/sitter -> 입력한 이메일로 가입한 회원이 이미 존재한다면 회원가입 요청이 실패한다.")
        @Test
        void createSitterAccountDuplicateEmail() {
            //given
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest(NAME, DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                    new SitterInfoRequest(SITTER_MIN_CARE_AGE, SITTER_MAX_CARE_AGE, ABOUT_ME)
            );
            시터_회원가입을_요청(request);
            SitterCreateRequest 이미_존재하는_계정ID를_가진_시터_회웝가입_요청 = new SitterCreateRequest(
                    new AccountCreateRequest("박시터",
                            LocalDate.of(1988, 3, 30),
                            "여",
                            "impark1988",
                            "password!@@12",
                            EMAIL),
                    new SitterInfoRequest(1, 3, "베테랑 시터입니다.")
            );

            // when
            ExtractableResponse<Response> response = 시터_회원가입을_요청(이미_존재하는_계정ID를_가진_시터_회웝가입_요청);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
            ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
            assertThat(exceptionResponse.getMessage()).isEqualTo("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
        }

        @DisplayName("POST /api/accounts/sitter -> 회원 이름에 공백이 포함되면 시터 회원가입 요청이 실패한다.")
        @Test
        void createSitterAccountNameHaveBlank() {
            //given
            SitterCreateRequest request = new SitterCreateRequest(
                    new AccountCreateRequest("박 민영", DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                    new SitterInfoRequest(SITTER_MIN_CARE_AGE, SITTER_MAX_CARE_AGE, ABOUT_ME)
            );

            // when
            ExtractableResponse<Response> response = 시터_회원가입을_요청(request);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
            assertThat(exceptionResponse.getMessage()).isEqualTo(Name.BLANK_EXCEPTION_MESSAGE);
        }

        private ExtractableResponse<Response> 시터_회원가입을_요청(SitterCreateRequest request) {
            return RestAssured
                    .given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/accounts/sitter")
                    .then().log().all()
                    .extract();
        }
    }
}
