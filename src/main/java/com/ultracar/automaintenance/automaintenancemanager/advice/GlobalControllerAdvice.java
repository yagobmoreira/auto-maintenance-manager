package com.ultracar.automaintenance.automaintenancemanager.advice;

import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handleNotFound(NotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }
}
