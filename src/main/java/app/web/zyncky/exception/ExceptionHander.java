package app.web.zyncky.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHander {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUserExistsException(UserExistsException ex, HttpServletRequest request) {
        return new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
    }

    @ExceptionHandler(UserMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUserMissingException(UserMissingException ex, HttpServletRequest request) {
        return new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        return new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(Exception ex, HttpServletRequest request) {
        return new ApiError(request.getRequestURI(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
    }

}
