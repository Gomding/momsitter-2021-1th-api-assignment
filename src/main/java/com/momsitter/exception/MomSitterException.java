package com.momsitter.exception;

import org.springframework.http.HttpStatus;

/**
 * httpStatus - 해당 예외의 Http 상태 코드
 * body - 해당 예외의 응답값
 */
public class MomSitterException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ExceptionResponse body;

    /**
     * super(body.getMessage()) 부분은 RuntimeException의 메세지를 설정한다.
     * 자바의 기존 예외를 다루는 라이브러리에서도 해당 예외를 다룰 수 있게하기 위함이다. ex) 테스트에서 예외 메세지 처리
     */
    public MomSitterException(HttpStatus httpStatus, ExceptionResponse body) {
        super(body.getMessage());
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExceptionResponse getBody() {
        return body;
    }
}
