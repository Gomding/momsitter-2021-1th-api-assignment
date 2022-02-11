package com.momsitter.api.acceptance;

import com.momsitter.authiorization.ui.dto.auth.TokenResponse;
import com.momsitter.common.exception.ExceptionResponse;
import com.momsitter.domain.Name;
import com.momsitter.service.dto.AccountCreateRequest;
import com.momsitter.service.dto.AccountInfoResponse;
import com.momsitter.service.dto.AccountResponse;
import com.momsitter.service.dto.AccountUpdateRequest;
import com.momsitter.service.dto.parent.*;
import com.momsitter.service.dto.sitter.SitterCreateRequest;
import com.momsitter.service.dto.sitter.SitterInfoRequest;
import com.momsitter.service.dto.sitter.SitterInfoResponse;
import com.momsitter.service.dto.sitter.SitterUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.momsitter.api.acceptance.AuthAcceptanceTest.로그인_되어_있음;
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
        // 시터로 회원 가입
        SitterCreateRequest createRequest = 시터_회원_가입_요청을_생성한다();
        ExtractableResponse<Response> createResponse = 시터_회원_가입을_요청(createRequest);
        시터_회원_생성됨(createResponse);

        // 로그인
        TokenResponse 사용자_인증토큰 = 로그인_되어_있음(ACCOUNT_ID, PASSWORD);

        // 시터 회원 정보 조회
        ExtractableResponse<Response> findResponse = 내_정보를_조회_요청(사용자_인증토큰);
        내_회원_정보_조회됨(findResponse);

        // 회원 정보 수정 요청
        AccountUpdateRequest updateRequest = 회원_정보_수정_요청을_생성한다();
        ExtractableResponse<Response> updateResponse = 내_회원_정보를_수정_요청(updateRequest, 사용자_인증토큰);
        내_회원_정보_수정됨(updateResponse);

        // 시터 정보 수정 요청
        SitterUpdateRequest sitterUpdateRequest = 시터_회원_정보_수정_요청을_생성한다();
        ExtractableResponse<Response> sitterUpdateResponse = 내_시터_회원_정보를_수정_요청(sitterUpdateRequest, 사용자_인증토큰);
        내_시터_회원_정보_수정됨(sitterUpdateResponse);

        // 시터 회원의 부모 회원으로도 활동하기
        ParentInfoRequest addActivityParentRequest = 시터_회원의_부모_회원으로도_활동하기를_요청을_생성한다();
        ExtractableResponse<Response> addParentInfoResponse = 시터_회원이_부모_정보_추가_요청(addActivityParentRequest, 사용자_인증토큰);
        시터_회원에_부모_정보_추가됨(addParentInfoResponse);
    }

    @DisplayName("부모 회원의 정보를 관리한다.")
    @Test
    void manageParentAccount() {
        // 부모로 회원 가입
        ParentCreateRequest request = 부모_회원_가입_요청을_생성한다();
        ExtractableResponse<Response> createResponse = 부모_회원_가입을_요청(request);
        부모_회원_생성됨(createResponse);

        // 로그인
        TokenResponse 사용자_인증토큰 = 로그인_되어_있음(ACCOUNT_ID, PASSWORD);

        // 내 정보 조회
        ExtractableResponse<Response> findResponse = 내_정보를_조회_요청(사용자_인증토큰);
        내_회원_정보_조회됨(findResponse);

        // 회원 정보 수정
        AccountUpdateRequest updateRequest = 회원_정보_수정_요청을_생성한다();
        ExtractableResponse<Response> updateResponse = 내_회원_정보를_수정_요청(updateRequest, 사용자_인증토큰);
        내_회원_정보_수정됨(updateResponse);

        // 부모 정보 수정
        ParentUpdateRequest parentUpdateRequest = 부모_회원_정보_수정_요청을_생성한다(사용자_인증토큰);
        ExtractableResponse<Response> parentUpdateResponse = 내_부모_회원_정보를_수정_요청(parentUpdateRequest, 사용자_인증토큰);
        내_부모_회원_정보_수정됨(parentUpdateResponse);

        // 부모 회원의 시터 회원으로도 활동하기
        SitterInfoRequest addActivityRequest = 부모_회원의_시터_회원으로도_활동하기_요청을_생성한다();
        ExtractableResponse<Response> addParentInfoResponse = 부모_회원의_시터회원으로_활동하기_요청(addActivityRequest, 사용자_인증토큰);
        부모_회원에_시터_정보_추가됨(addParentInfoResponse);
    }

    private SitterInfoRequest 부모_회원의_시터_회원으로도_활동하기_요청을_생성한다() {
        return new SitterInfoRequest(2, 4, "언제나 내 아이처럼!, 내 가족처럼!");
    }

    private ExtractableResponse<Response> 부모_회원의_시터회원으로_활동하기_요청(SitterInfoRequest request, TokenResponse token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token.getAccessToken())
                .body(request)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/accounts/me/sitter")
                .then().log().all()
                .extract();
    }

    private void 부모_회원에_시터_정보_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        SitterInfoResponse sitterInfoResponse = response.as(SitterInfoResponse.class);
        assertThat(sitterInfoResponse.getId()).isNotNull();
    }

    public static SitterCreateRequest 시터_회원_가입_요청을_생성한다() {
        return new SitterCreateRequest(
                new AccountCreateRequest(NAME, DATE_OF_BIRTH, GENDER, ACCOUNT_ID, PASSWORD, EMAIL),
                new SitterInfoRequest(SITTER_MIN_CARE_AGE, SITTER_MAX_CARE_AGE, ABOUT_ME)
        );
    }

    public static ExtractableResponse<Response> 시터_회원_가입을_요청(SitterCreateRequest request) {
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
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/accounts/parent")
                .then().log().all()
                .extract();
    }

    private void 부모_회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    private ExtractableResponse<Response> 내_정보를_조회_요청(TokenResponse token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/accounts/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    private void 내_회원_정보_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AccountInfoResponse accountInfoResponse = response.as(AccountInfoResponse.class);
        assertThat(accountInfoResponse.getAccount()).isNotNull();
    }

    private AccountUpdateRequest 회원_정보_수정_요청을_생성한다() {
        return new AccountUpdateRequest("여", "newpw12!!@@", "newtest@email.com");
    }

    private ExtractableResponse<Response> 내_회원_정보를_수정_요청(AccountUpdateRequest updateRequest, TokenResponse token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token.getAccessToken())
                .body(updateRequest)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/accounts/me")
                .then().log().all()
                .extract();
    }

    private void 내_회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AccountResponse accountResponse = response.as(AccountResponse.class);
        assertThat(accountResponse.getId()).isNotNull();
    }

    private SitterUpdateRequest 시터_회원_정보_수정_요청을_생성한다() {
        return new SitterUpdateRequest(SITTER_MIN_CARE_AGE + 1, SITTER_MAX_CARE_AGE + 1,
                ABOUT_ME + "update");
    }

    private ExtractableResponse<Response> 내_시터_회원_정보를_수정_요청(SitterUpdateRequest request, TokenResponse token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token.getAccessToken())
                .body(request)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/accounts/me/sitter")
                .then().log().all()
                .extract();
    }

    private void 내_시터_회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        SitterInfoResponse updateResponse = response.as(SitterInfoResponse.class);
        assertThat(updateResponse.getId()).isNotNull();
    }

    private ParentUpdateRequest 부모_회원_정보_수정_요청을_생성한다(TokenResponse token) {
        AccountInfoResponse accountInfoResponse = 내_정보를_조회_요청(token).as(AccountInfoResponse.class);
        List<ChildResponse> children = accountInfoResponse.getParent().getChildren();
        List<ChildUpdateRequest> childUpdateRequests = children.stream()
                .map(child -> new ChildUpdateRequest(child.getId(), child.getDateOfBirth().plusDays(1L), "여"))
                .collect(Collectors.toList());
        return new ParentUpdateRequest(childUpdateRequests, "시터님 잘부탁드려요!");
    }

    private ExtractableResponse<Response> 내_부모_회원_정보를_수정_요청(ParentUpdateRequest request, TokenResponse token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token.getAccessToken())
                .body(request)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/accounts/me/parent")
                .then().log().all()
                .extract();
    }

    private void 내_부모_회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ParentInfoResponse parentInfoResponse = response.as(ParentInfoResponse.class);
        assertThat(parentInfoResponse.getId()).isNotNull();
    }

    private ParentInfoRequest 시터_회원의_부모_회원으로도_활동하기를_요청을_생성한다() {
        ChildRequest childRequest1 = new ChildRequest(CHILD_DATE_OF_BIRTH1, CHILD_GENDER1);
        ChildRequest childRequest2 = new ChildRequest(CHILD_DATE_OF_BIRTH2, CHILD_GENDER2);
        return new ParentInfoRequest(Arrays.asList(childRequest1, childRequest2), PARENT_CARE_REQUEST_INFO);
    }

    private ExtractableResponse<Response> 시터_회원이_부모_정보_추가_요청(ParentInfoRequest request, TokenResponse token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token.getAccessToken())
                .body(request)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/accounts/me/parent")
                .then().log().all()
                .extract();
    }

    private void 시터_회원에_부모_정보_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ParentInfoResponse parentInfoResponse = response.as(ParentInfoResponse.class);
        assertThat(parentInfoResponse.getId()).isNotNull();
    }

    @DisplayName("[공통]회원가입 시 잘못된 요청 테스트")
    @Nested
    class CreateSitterAccountTest {
        @DisplayName("POST /api/accounts/sitter -> 이미 존재하는 계정ID로 시터 회원가입을 요청하면 실패한다.")
        @Test
        void createSitterAccountDuplicateAccountId() {
            //given
            SitterCreateRequest request = 시터_회원_가입_요청을_생성한다();
            시터_회원_가입을_요청(request);
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
            ExtractableResponse<Response> response = 시터_회원_가입을_요청(이미_존재하는_계정ID를_가진_시터_회웝가입_요청);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
            ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
            assertThat(exceptionResponse.getMessage()).isEqualTo("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
        }

        @DisplayName("POST /api/accounts/sitter -> 입력한 이메일로 가입한 회원이 이미 존재한다면 회원가입 요청이 실패한다.")
        @Test
        void createSitterAccountDuplicateEmail() {
            //given
            SitterCreateRequest request = 시터_회원_가입_요청을_생성한다();
            시터_회원_가입을_요청(request);
            SitterCreateRequest 이미_존재하는_이메일을_가진_시터_회웝가입_요청 = new SitterCreateRequest(
                    new AccountCreateRequest("박시터",
                            LocalDate.of(1988, 3, 30),
                            "여",
                            "impark1988",
                            "password!@@12",
                            EMAIL),
                    new SitterInfoRequest(1, 3, "베테랑 시터입니다.")
            );

            // when
            ExtractableResponse<Response> response = 시터_회원_가입을_요청(이미_존재하는_이메일을_가진_시터_회웝가입_요청);

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
            ExtractableResponse<Response> response = 시터_회원_가입을_요청(request);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
            assertThat(exceptionResponse.getMessage()).isEqualTo(Name.BLANK_EXCEPTION_MESSAGE);
        }
    }
}
