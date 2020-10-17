package com.codecool.webshopbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ MaxUploadSizeExceededException.class })
    public ResponseEntity<?> handleMaxUploadSizeExceededException(Exception ex, WebRequest request) {
        Map<Object, Object> model = new HashMap<>();
        model.put("MaxUploadSizeExceededException", "confirmed");
        return ResponseEntity.badRequest().body(model);
    }
}
