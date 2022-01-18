package com.momsitter.ui;

import com.momsitter.exception.ExceptionResponse;
import com.momsitter.exception.MomSitterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(MomSitterException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MomSitterException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getBody());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(Exception e) {
        log.error(e.getMessage());
        StringBuilder sb = new StringBuilder();
        String[] spiltMessages = Arrays.toString(e.getStackTrace()).split(",");
        for (String splitMessage : spiltMessages) {
            sb.append(splitMessage).append("\n");
        }
        log.error(sb.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("맘시터 관리자에게 문의해주세요");
    }
}
