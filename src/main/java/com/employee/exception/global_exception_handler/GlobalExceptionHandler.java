package com.employee.exception.global_exception_handler;

import com.employee.exception.ErrorResponse.ErrorResponse;
import com.employee.exception.custom_exception.EmployeeAlreadyExistsException;
import com.employee.exception.custom_exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse<Object>> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
        ErrorResponse errorResponse = ErrorResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<Object>> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex){
        ErrorResponse errorResponse = ErrorResponse.error(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String,String>>> handleValidationException(MethodArgumentNotValidException ex){
        HashMap<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(er->{
           String fieldName = ((FieldError)er).getField();
            String Message = er.getDefaultMessage();
        });
        ErrorResponse<Map<String,String>> errorResponse=new ErrorResponse<Map<String,String>>(false,"Validation Error",HttpStatus.BAD_REQUEST,errors);
        return new ResponseEntity<ErrorResponse<Map<String,String>>>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handleGlobalException(Exception ex){
        ErrorResponse errorResponse = ErrorResponse.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
