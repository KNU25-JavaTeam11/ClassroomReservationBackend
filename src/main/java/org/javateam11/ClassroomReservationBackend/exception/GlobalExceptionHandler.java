package org.javateam11.ClassroomReservationBackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        String message = String.format("%s 파라미터는 필수입니다.", paramName);
        return ResponseEntity.badRequest().body(Map.of("error", message));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String paramType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "알 수 없음";
        String message = String.format("%s 파라미터의 형식이 올바르지 않습니다. %s 형식이어야 합니다.", paramName, paramType);
        return ResponseEntity.badRequest().body(Map.of("error", message));
    }
} 