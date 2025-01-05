package airlines.application;

import airlines.exceptions.InvalidSortFieldException;
import airlines.exceptions.PageNotFoundException;
import airlines.exceptions.notfounds.AircraftNotFoundException;
import airlines.exceptions.notfounds.ManufacturerNotFoundException;
import airlines.exceptions.duplicates.DuplicateAircraftException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_FAILED = "Validation failed";
    private static final String DATABASE_ERROR = "Database error";
    private static final String INVALID_PAGINATION_PARAMETERS = "Invalid pagination parameters";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred";

    // 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseHandler.generateResponse(VALIDATION_FAILED, HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> errors.put(
                violation.getPropertyPath().toString().split("\\.")[1], violation.getMessage())
        );
        return ResponseHandler.generateResponse(INVALID_PAGINATION_PARAMETERS, HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> handlePropertyReferenceException(PropertyReferenceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Invalid field", ex.getPropertyName());
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(InvalidSortFieldException.class)
    public ResponseEntity<Object> handleInvalidSortFieldException(InvalidSortFieldException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Invalid value", Objects.requireNonNull(ex.getValue()).toString());
        errors.put("requiredType", Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        errors.put("parameter", ex.getName());
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    // 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<Object> handlePageNotFoundException(PageNotFoundException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(AircraftNotFoundException.class)
    public ResponseEntity<Object> handleAircraftNotFoundException(AircraftNotFoundException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(ManufacturerNotFoundException.class)
    public ResponseEntity<Object> handleManufacturerNotFoundException(ManufacturerNotFoundException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    // 409 Conflict
    @ExceptionHandler(DuplicateAircraftException.class)
    public ResponseEntity<Object> handleDuplicateAircraftException(DuplicateAircraftException ex) {
        return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, null);
    }

    // 500 Internal Server Error
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        return ResponseHandler.generateResponse(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseHandler.generateResponse(UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<Object> handleJpaSystemException(JpaSystemException ex) {
        return ResponseHandler.generateResponse(DATABASE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}