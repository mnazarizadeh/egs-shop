package com.egs.shop.handler;

import com.egs.shop.exception.EGSRuntimeException;
import com.egs.shop.model.constant.ExceptionMessages;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ExceptionMessages.MALFORMED_JSON_REQUEST, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        ApiError apiError = new ApiError(status);
        apiError.setMessage(status.getReasonPhrase());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            ApiValidationError apiValidationError = ApiValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .rejectedValue(fieldError.getRejectedValue())
                    .object(fieldError.getObjectName())
                    .build();

            apiError.addSubError(apiValidationError);
        }

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(EGSRuntimeException.class)
    public ResponseEntity<Object> handleMyExceptions(EGSRuntimeException exception) {
        log.error("EGS specific exception occurred.", exception);
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);

        logger.debug("Exception response status is " + responseStatus.code().value());
        ApiError apiError = new ApiError(responseStatus.code(), exception.getMessage());

        return ResponseEntity.status(responseStatus.code()).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationExceptions(AuthenticationException exception) {
        log.error("Authentication exception occurred.", exception);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedExceptions(AccessDeniedException exception) {
        log.error("Access denied  exception occurred.", exception);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonExceptions(Exception exception) {
        log.error("Common exception occurred.", exception);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.INTERNAL_ERROR);
        apiError.setDebugMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDatabaseExceptions(DataIntegrityViolationException exception) {
        log.error("Database exception occurred.", exception);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.DB_ERROR);
        apiError.setDebugMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    /**
     * Customize the response for BindException.
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleBindException(BindException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        ApiError apiError = new ApiError(status);
        apiError.setMessage(status.getReasonPhrase());
        for (FieldError fieldError : ex.getFieldErrors()) {
            ApiValidationError apiValidationError = ApiValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .rejectedValue(fieldError.getRejectedValue())
                    .object(fieldError.getObjectName())
                    .build();

            apiError.addSubError(apiValidationError);
        }

        return ResponseEntity.badRequest().body(apiError);
    }
}