package com.gurukul.hub.controller;

import com.gurukul.hub.service.HubService;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;


@RestController
@RequestMapping("/hub")
public class HubController {

    @Autowired
    private HubService hubService;

    @PostMapping("/")
    public Message postMessage(@Valid @RequestBody Message msg) {

        return hubService.postMessage(msg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            System.out.println("errors*** " + errors);
        });
        return errors;
    }
}
