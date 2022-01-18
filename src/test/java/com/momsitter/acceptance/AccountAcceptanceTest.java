package com.momsitter.acceptance;

import com.momsitter.ui.account.dto.AccountCreateRequest;
import com.momsitter.ui.account.dto.SitterCreateRequest;
import com.momsitter.ui.account.dto.SitterInfoRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 관련 인수 테스트")
class AccountAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUpOnAccountAcceptance() {
        super.setUp();
    }

    @AfterEach
    void clearOnAccountAcceptance() {
        super.clear();
    }

    @DisplayName("시터 회원가입 테스트")
    @Nested
    class CreateSitterAccountTest {

        @DisplayName("POST /api/accounts/sitter -> 시터 회원가입 요청이 정상적으로 된다.")
        @Test
        void createSitterAccount() {
            //given
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
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().post("/api/accounts/sitter")
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).isNotBlank();
        }
    }
}
