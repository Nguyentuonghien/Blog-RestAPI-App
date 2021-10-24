package com.springboot.restapi.blog.exception;

import org.springframework.http.HttpStatus;

/*
 *  ném ra exception bất cứ khi nào ta xử lý một số business logic hoặc xác thực request parameter
 *
 * */
public class BlogAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BlogAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
