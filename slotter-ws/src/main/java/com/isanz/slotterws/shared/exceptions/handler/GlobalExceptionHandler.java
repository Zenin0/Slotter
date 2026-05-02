package com.isanz.slotterws.shared.exceptions.handler;

import com.isanz.slotterws.shared.exceptions.GenericException;
import com.isanz.slotterws.shared.exceptions.alreadyexists.CompanyAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.alreadyexists.RoleAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.alreadyexists.UserAlreadyExistsException;
import com.isanz.slotterws.shared.exceptions.auth.InvalidCredentialsException;
import com.isanz.slotterws.shared.exceptions.auth.InvalidSessionException;
import com.isanz.slotterws.shared.exceptions.auth.SessionExpiredException;
import com.isanz.slotterws.shared.exceptions.notfound.CompanyNotFoundException;
import com.isanz.slotterws.shared.exceptions.notfound.RoleNotFoundException;
import com.isanz.slotterws.shared.exceptions.notfound.UserNotFoundException;
import com.isanz.slotterws.shared.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleCompanyNotFound(CompanyNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(RoleNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleCompanyAlreadyExists(CompanyAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleCompanyAlreadyExists(RoleAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentials(InvalidCredentialsException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidSession(InvalidSessionException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<ApiResponse<?>> handleSessionExpired(SessionExpiredException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(GenericException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleUnexpected(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Unhandled error, or exception " + ex.getClass().getName() + " " + ex.getMessage()));
    }
}