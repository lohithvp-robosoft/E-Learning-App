package com.robosoft.elearning.exception;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    private static final Log log = LogFactory.getLog(ExceptionHandler.class);

    @Autowired
    private ResponseUtil responseUtil;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public <T> ResponseEntity<ResponseDTO<T>> handleEmptyEmployeeListException() {
        return responseUtil.errorResponse("Email is already registered");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {InvalidCredentialsException.class})
    public <T> ResponseEntity<ResponseDTO<T>> handleInvalidCredentialsException() {
        return responseUtil.errorResponse("Invalid email or Password", 401);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotFoundException.class})
    public <T> ResponseEntity<ResponseDTO<T>> handleNotFoundException(NotFoundException ex) {
        return responseUtil.errorResponse(ex.getMessage(), 404);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public <T> ResponseEntity<ResponseDTO<T>> handleGenericException(Exception ex) {
        log.error(ex);
        log.error("Error message " + ex.getMessage());
        return responseUtil.errorResponse("Internal Server Error: " + ex.getMessage(), 500);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDTO<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return responseUtil.errorResponse("Validation error occurred ", 400);
    }
}

