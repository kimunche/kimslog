package com.kimslog.controller;

import com.kimslog.request.PostCreate;
import com.kimslog.request.PostEdit;
import com.kimslog.request.PostSearch;
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

    /*
    * 게시글 작성
    * */
    @PostMapping ("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        //log.info("params={}", params.toString());
//        if(request.getTitle().contains("바보")){
//            throw new InvalidRequest();
//        }
        //데이터를 꺼내서 직접 조합하고 검증하는 것 보다 메시지 던지는 것으로 처리
        request.validate();
        postService.write(request);
    }
    /*
    * /post 글 전체 조회(검색+페이징)
    * /post/{postId} -> 글 한개만 조회
    * */

    //글 한건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }

    //글 여러개 조회
    @GetMapping("/posts")
    public List<PostResponse> getPostList(@ModelAttribute PostSearch postSearch){
        return postService.getPostList(postSearch);
    }

    @PatchMapping ("/posts/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        postService.editPost(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);

    }

}
