package dhbart.portfolioapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import dhbart.portfolioapi.assistant.service.OpenAiResilienceService.OpenAiCommunicationException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiErrorResponse> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fields.putIfAbsent(error.getField(),
                error.getDefaultMessage() == null ? "Invalid value" : error.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "Request validation failed", request, fields);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiErrorResponse> constraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, "Request validation failed", request, Map.of());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingRequestHeaderException.class,
            BusinessException.class})
    ResponseEntity<ApiErrorResponse> badRequest(Exception ex, HttpServletRequest request) {
        String message = ex instanceof BusinessException ? ex.getMessage() : "Malformed request";
        return response(HttpStatus.BAD_REQUEST, message, request, Map.of());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    ResponseEntity<ApiErrorResponse> rateLimited(RateLimitExceededException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).header("Retry-After", "60")
                .body(error(HttpStatus.TOO_MANY_REQUESTS, "Request rate limit exceeded", request, Map.of()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    ResponseEntity<ApiErrorResponse> denied(Exception ex, HttpServletRequest request) {
        return response(HttpStatus.FORBIDDEN, "Access denied", request, Map.of());
    }

    @ExceptionHandler({AuthenticationException.class})
    ResponseEntity<ApiErrorResponse> unauthenticated(Exception ex, HttpServletRequest request) {
        return response(HttpStatus.UNAUTHORIZED, "Authentication required", request, Map.of());
    }

    @ExceptionHandler({TimeoutException.class, OpenAiCommunicationException.class})
    ResponseEntity<ApiErrorResponse> upstream(Exception ex, HttpServletRequest request) {
        return response(HttpStatus.SERVICE_UNAVAILABLE, "Assistant service is temporarily unavailable", request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> unexpected(Exception ex, HttpServletRequest request) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal server error", request, Map.of());
    }

    private ResponseEntity<ApiErrorResponse> response(HttpStatus status, String message,
                                                       HttpServletRequest request, Map<String, String> fields) {
        return ResponseEntity.status(status).body(error(status, message, request, fields));
    }

    private ApiErrorResponse error(HttpStatus status, String message, HttpServletRequest request,
                                   Map<String, String> fields) {
        return new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message,
                request.getRequestURI(), fields);
    }
}
