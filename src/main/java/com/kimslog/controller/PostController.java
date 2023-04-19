package com.kimslog.controller;

import com.kimslog.request.PostCreate;
import com.kimslog.response.PostResponse;
import com.kimslog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }

    //글 여러개 조회
    @GetMapping("/posts")
    public List<PostResponse> getPostList(){
        return postService.getPostList();
    }

}
