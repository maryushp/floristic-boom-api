package com.floristicboom.utils.exceptionhandler;

import com.floristicboom.utils.exceptionhandler.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.cache.CacheException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.security.SignatureException;

import static com.floristicboom.utils.Constants.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DefaultExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Invalid request";
        Problem problem = buildProblem(Status.BAD_REQUEST, "Invalid request", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(NoSuchItemException.class)
    public ResponseEntity<Problem> handleNoSuchObjectException(NoSuchItemException e) {
        Problem problem = buildProblem(Status.NOT_FOUND, NO_SUCH_ITEM_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler({ItemAlreadyExistsException.class, UserAlreadyRegisteredException.class})
    public ResponseEntity<Problem> handleObjectAlreadyExistsException(RuntimeException e) {
        Problem problem = buildProblem(Status.CONFLICT, ALREADY_EXIST_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler(FlowerUnavaliableException.class)
    public ResponseEntity<Problem> handleFlowerUnavaliableException(FlowerUnavaliableException e) {
        Problem problem = buildProblem(Status.CONFLICT, FLOWER_UNAVALIABLE, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
    }

    @ExceptionHandler({ExpiredJwtException.class, SignatureException.class,
            MalformedJwtException.class})
    public ResponseEntity<Problem> handleJwtExceptions(RuntimeException e) {
        Problem problem = buildProblem(Status.UNAUTHORIZED, AUTHENTICATION_EXCEPTION, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Problem> handleBadCredentials() {
        Problem problem = buildProblem(Status.UNAUTHORIZED, AUTHENTICATION_EXCEPTION, WRONG_EMAIL_OR_PASSWORD);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }

    @ExceptionHandler({CacheException.class, IncorrectTokenTypeException.class})
    public ResponseEntity<Problem> handleCacheError() {
        Problem problem = buildProblem(Status.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR.toString(),
                AN_INTERNAL_SERVER_ERROR_OCCURRED_WHILE_PROCESSING_THE_REQUEST);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler({InvalidTokenException.class, TokenRequiredException.class})
    public ResponseEntity<Problem> handleTokenExceptions(RuntimeException ex) {
        Problem problem = buildProblem(Status.BAD_REQUEST, TOKEN_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    private Problem buildProblem(Status status, String title, String detail) {
        return Problem.builder()
                .withStatus(status)
                .withTitle(title)
                .withDetail(detail)
                .build();
    }
}