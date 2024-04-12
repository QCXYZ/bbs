package com.bbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception ex) {
        Map<String, String> response = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            e.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                response.put(fieldName, errorMessage);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (ex instanceof NullPointerException) {
            response.put("信息", "空指针异常");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


        } else if (ex instanceof ResourceNotFoundException) {
            response.put("信息", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);


        } else if (ex instanceof RuntimeException) {
            response.put("信息", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {  // 通用异常处理
            response.put("信息", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
