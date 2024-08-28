package com.caselab.testtask.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@Builder
public class HttpResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;

    public HttpResponseException(HttpStatus httpStatus, Integer httpStatusCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
