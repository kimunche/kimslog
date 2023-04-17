package com.kimslog.service;

import com.kimslog.domain.Post;
import com.kimslog.repository.PostRepository;
import com.kimslog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository; //생성자 injection

    public void write(PostCreate postCreate){
        //postcreate (일반클래스)=> entity 형태로 변환
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }
}
