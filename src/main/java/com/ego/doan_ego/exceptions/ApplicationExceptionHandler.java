package com.ego.doan_ego.exceptions;

import com.ego.doan_ego.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<?> handleJwtExpired(ExpiredJwtException e){
        return ApiResponse.tokenExpired();
    }
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<?> handleJwtNotValid(MalformedJwtException e){
        return ApiResponse.tokenNotvalid();
    }
    @ExceptionHandler(io.jsonwebtoken.SignatureException.class)
    @ResponseBody
    public ResponseEntity<?> handleJwtNotValid(SignatureException e){
        return ApiResponse.tokenNotvalid();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
        return ApiResponse.accessDenied();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException noHandlerFoundException) {
        return ApiResponse.notFound(noHandlerFoundException.getRequestURL());
    }
}
