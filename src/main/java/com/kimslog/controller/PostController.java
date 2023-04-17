package com.kimslog.controller;

import com.kimslog.request.PostCreate;
import com.kimslog.service.PostService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping ("/post")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) {
        //log.info("params={}", params.toString());
        postService.write(request);
        return Map.of();
    }

}
