package com.employee.exception.ErrorResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {
    private Boolean success;
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;
    private T data;
    private String error;

    public ErrorResponse(String message, HttpStatus notFound) {
    }

    public ErrorResponse(Boolean success, String message, HttpStatus httpStatus, T data) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime= LocalDateTime.now();
        this.data = data;
    }

    public ErrorResponse(Boolean success, String message, HttpStatus httpStatus, String error) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime= LocalDateTime.now();
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static <T>ErrorResponse<T> success(T data,String message){
        return new ErrorResponse<>(true,message,HttpStatus.OK,data);
    }

    public static <T>ErrorResponse<T> create(T data,String message){
        return new ErrorResponse<>(true,message,HttpStatus.CREATED,data);
    }

    public static <T>ErrorResponse<T> error(String message,HttpStatus httpStatus){
        return new ErrorResponse<>(false,message,httpStatus,message);
    }
}
