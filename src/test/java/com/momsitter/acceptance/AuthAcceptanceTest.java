package com.momsitter.acceptance;

import com.momsitter.exception.ExceptionResponse;
import com.momsitter.ui.dto.auth.TokenRequest;
import com.momsitter.ui.dto.auth.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.momsitter.acceptance.AccountAcceptanceTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인 관련 인수 테스트")
class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원이 로그인을 진행한다.")
    @Test
    void login() {
        시터_회원_가입을_요청(시터_회원_가입_요청을_생성한다());

        ExtractableResponse<Response> response = 로그인_요청(new TokenRequest(ACCOUNT_ID, PASSWORD));
        인증_토큰_생성됐다(response);

        ExtractableResponse<Response> response2 = 로그인_요청(new TokenRequest("notExistsAccountId", PASSWORD));
        존재하지_않는_아이디로_로그인을_시도하면_실패한다(response2);

        ExtractableResponse<Response> response3 = 로그인_요청(new TokenRequest(ACCOUNT_ID, "failpw123!"));
        아이디에_비밀번호가_일치하지않아_로그인에_실패한다(response3);
    }

    public static TokenResponse 로그인_되어_있음(String accountId, String password) {
        TokenRequest request = new TokenRequest(accountId, password);

        ExtractableResponse<Response> response = 로그인_요청(request);
        return response.as(TokenResponse.class);
    }

    private static ExtractableResponse<Response> 로그인_요청(TokenRequest request) {
        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(request).
                when().
                post("/api/login").
                then().
                log().all().
                extract();
    }

    private void 인증_토큰_생성됐다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        TokenResponse tokenResponse = response.as(TokenResponse.class);
        assertThat(tokenResponse.getValue()).isNotNull();
    }

    private void 존재하지_않는_아이디로_로그인을_시도하면_실패한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
    }

    private void 아이디에_비밀번호가_일치하지않아_로그인에_실패한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        ExceptionResponse exceptionResponse = response.as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
    }
}
