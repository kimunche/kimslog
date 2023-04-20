package com.kimslog.service;

import com.kimslog.domain.Post;
import com.kimslog.repository.PostRepository;
import com.kimslog.request.PostCreate;
import com.kimslog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
        //junit5 에서 test 메서드들이 각각 수행되기 전에 항상 수행이 되도록 보당
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void writeContent(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getContent(){
        //given requestPost엔티티 저장
        Post post = Post.builder()
                .title("1234567891011")
                .content("content")
                .build();
        postRepository.save(post);

        //when 해당 id 로 조회
        PostResponse response = postService.get(post.getId());

        //then null이면 안됨
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("title", response.getTitle());
        assertEquals("content", response.getContent());
    }

    @Test
    @DisplayName("글 첫페이지 조회")
    void getContents(){
        //given requestPost엔티티 저장
//        for(int i=0; i<30; i++){
//
//        }
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i-> { return Post.builder()
                            .title("제목 - "+ i)
                            .content("테스트 - "+i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC, "id");

        //when 해당 id 로 조회
        List<PostResponse> posts = postService.getPostList(pageable);

        //then null이면 안됨
        assertEquals(5L, posts.size());
        assertEquals("제목 - 30", posts.get(0).getTitle());
        assertEquals("제목 - 26", posts.get(4).getTitle());

    }
}