package app.web.zyncky.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHander {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleUserExistsException(UserExistsException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleUserMissingException(UserMissingException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex,
            HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex,
            HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
