package com.kimslog.controller;

import com.kimslog.domain.Post;
import com.kimslog.request.PostCreate;
import com.kimslog.response.PostResponse;
import com.kimslog.service.PostService;
import lombok.Getter;
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
    public void post(@RequestBody @Valid PostCreate request) {
        //log.info("params={}", params.toString());
        postService.write(request);
    }
    /*
    * /post 글 전체 조회(검색+페이징)
    * /post/{postId} -> 글 한개만 조회
    * */

    @GetMapping("/post/{postId}")
    public PostResponse get(@PathVariable(name="postId") Long id){
        PostResponse response = postService.get(id);
        return response;
    }

}
