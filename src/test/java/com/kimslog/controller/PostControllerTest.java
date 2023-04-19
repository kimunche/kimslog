package com.kimslog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimslog.domain.Post;
import com.kimslog.repository.PostRepository;
import com.kimslog.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SuppressWarnings("ALL")
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach //junit5 에서 test 메서드들이 각각 수행되기 전에 항상 수행이 되도록 보당
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/post 요청시 hello 출력")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("글")
                .build();

        //ObjectMapper objectMapper = new ObjectMapper(); //json 프로세싱 라이브러리
        String json = objectMapper.writeValueAsString(request);

        System.out.println(json); //json 잘생성됐나 확인
        //expected
        mockMvc.perform(post("/post")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print()); //http 요청에 대한 summary 남겨줌
    }

    @Test
    @DisplayName("/post 요청시 title 값 필수")
    void DataTest() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("글")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/post")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 입력입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print()); //http 요청에 대한 summary 남겨줌
    }

    @Test
    @DisplayName("/post 요청시 db에 값이 저장")
    void DBPostTest() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("글")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/post")
                .contentType(APPLICATION_JSON)
                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("글", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getContent() throws Exception {
        //given
        Post post = Post.builder()
                .title("1234567891011")
                .content("content")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/post/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567891"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(print());
        //then
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getContents() throws Exception {
        //given
        Post post1 = Post.builder()
                .title("1234567891011")
                .content("content")
                .build();

        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("1234567891011")
                .content("content")
                .build();

        postRepository.save(post2);

        //expected
        mockMvc.perform(get("/posts")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        //then
    }
}

