package com.kimslog.controller;

import com.kimslog.domain.Post;
import com.kimslog.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SuppressWarnings("ALL")
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

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
        //expected
        mockMvc.perform(post("/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"제목\", \"content\": \"글\" }")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"))
                .andDo(print()); //http 요청에 대한 summary 남겨줌
    }

    @Test
    @DisplayName("/post 요청시 title 값 필수")
    void DataTest() throws Exception {
        //expected
        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"\", \"content\": \"글\" }")
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
        //when
        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"제목\", \"content\": \"글\" }")
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("글", post.getContent());
    }
}