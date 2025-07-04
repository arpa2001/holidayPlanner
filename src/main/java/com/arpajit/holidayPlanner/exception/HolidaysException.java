package com.arpajit.holidayPlanner.exception;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.arpajit.holidayPlanner.dto.*;

@RestControllerAdvice
public class HolidaysException {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String methodUsed = e.getMethod();
        Set<String> allowedMethods = e.getSupportedHttpMethods()
                                        .stream()
                                        .map((HttpMethod method) -> method.name())
                                        .collect(Collectors.toSet());
        String message = "Method " + methodUsed + " is not allowed. Supported method(s): " + allowedMethods;
        ExceptionResponse error = new ExceptionResponse("FAILURE: METHOD_NOT_ALLOWED", message);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFound(NoResourceFoundException e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: RESOURCE_NOT_FOUND",
                                                        e.getMessage()+" Please recheck the URL");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        String receivedType = e.getContentType() != null ? e.getContentType().toString() : "unknown";
        List<String> supportedTypes = e.getSupportedMediaTypes()
                                        .stream()
                                        .map(MediaType::toString)
                                        .collect(Collectors.toList());
        String message = "Content type '" + receivedType + "' is not supported. Supported type(s): " + supportedTypes;
        ExceptionResponse error = new ExceptionResponse("FAILURE: UNSUPPORTED_MEDIA_TYPE", message);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleMessageNotReadable(HttpMessageNotReadableException e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: BAD_REQUEST", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointer(NullPointerException e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: FORBIDDEN", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: BAD_REQUEST", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntime(RuntimeException e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: FORBIDDEN", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenaral(Exception e) {
        ExceptionResponse error = new ExceptionResponse("FAILURE: "+e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
