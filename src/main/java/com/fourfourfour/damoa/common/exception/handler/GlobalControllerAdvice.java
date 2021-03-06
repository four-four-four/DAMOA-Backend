package com.fourfourfour.damoa.common.exception.handler;

import com.fourfourfour.damoa.common.dto.response.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        logPrint(e);

        Map<String, Object> data = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", fieldError.getField());
            errorInfo.put("message", fieldError.getDefaultMessage());
            data.put("fieldError", errorInfo);
        }

        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            Map<String, String> errorInfo = new HashMap<>();
            errorInfo.put("field", globalError.getObjectName());
            errorInfo.put("message", globalError.getDefaultMessage());
            data.put("globalError", errorInfo);
        }

        return ErrorResponseDto.<Map<String, Object>>builder()
                .data(data)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponseDto<?> accessDeniedExceptionHandler(AccessDeniedException e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponseDto<?> exceptionHandler(Exception e) {
        logPrint(e);
        return ErrorResponseDto.builder()
                .build();
    }

    private void logPrint(Exception e) {
        log.error("[exceptionHandler] ex", e);
    }
}
