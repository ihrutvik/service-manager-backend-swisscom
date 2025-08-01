package com.swisscom.servicemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a Service with a given ID is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)  // Marks this exception to map to a 404 response
public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String message) {
        super(message);
    }
}
