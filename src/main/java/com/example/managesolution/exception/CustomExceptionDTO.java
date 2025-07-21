package com.example.managesolution.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomExceptionDTO {
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public CustomExceptionDTO(ErrorCode errorCode) {
        this.status = errorCode.getHttpStatus().value();
        this.code = errorCode.name();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getErrorMessage();
    }
}
