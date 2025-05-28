package com.backend.app.util;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private HttpStatus statusCode;
    private String message;
    private T data;

    public ApiResponse(T data, String message, HttpStatus statusCode) {
        this.statusCode = statusCode;
        this.message = message != null ? message : "success";
        this.data = data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
