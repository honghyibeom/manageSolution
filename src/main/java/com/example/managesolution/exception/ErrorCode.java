package com.example.managesolution.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_EXIST_USER("회원님이 없습니다.", HttpStatus.BAD_REQUEST),
    EXCEED_REMAINING_COUNT("수업 횟수를 초과했습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_TIME("startDate/endDate/time 은 필수입니다.", HttpStatus.BAD_REQUEST),
    END_DATE_BEFORE("endDate 가 startDate 이전입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_FIELD("필수 필드가 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_DATES("선택된 기간/요일에 해당하는 날짜가 없습니다.", HttpStatus.BAD_REQUEST),
    EXIST_SESSION("해당 회원의 수업이 이미 존재합니다.", HttpStatus.BAD_REQUEST);


    private final HttpStatus httpStatus;
    private final String errorMessage;
    ErrorCode(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
