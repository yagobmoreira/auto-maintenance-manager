package com.ultracar.automaintenance.automaintenancemanager.advice;

import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.NotFoundException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Global controller advice.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

  /**
   * Handle not found response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<String> handleNotFound(NotFoundException exception) {
    return ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(exception.getMessage());
  }

  /**
   * Handle business exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<String> handleBusinessException(BusinessException exception) {
    return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(exception.getMessage());
  }

  /**
   * Handle method argument not valid response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    List<String> errorMessages = e.getBindingResult()
                                     .getFieldErrors()
                                     .stream()
                                     .map(FieldError::getDefaultMessage)
                                     .collect(Collectors.toList());

    return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(String.join(", ", errorMessages));
  }

  /**
   * Handle date time parse exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler
  public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException e) {
    return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body("Formato de data inválido.");
  }
}
