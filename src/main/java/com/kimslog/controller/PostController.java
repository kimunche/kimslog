package com.kimslog.controller;

import com.kimslog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping ("/post")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        log.info("params={}", params.toString());
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String errorMessage =firstFieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName,errorMessage);
            return error;
        }

        return Map.of();
    }

}
