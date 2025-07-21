package com.example.managesolution.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_EXIST_USER("회원님이 없습니다.", HttpStatus.BAD_REQUEST),
    EXCEED_REMAINING_COUNT("수업 횟수를 초과했습니다.", HttpStatus.BAD_REQUEST);



    private final HttpStatus httpStatus;
    private final String errorMessage;
    ErrorCode(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
