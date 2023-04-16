package com.kimslog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SuppressWarnings("ALL")
@WebMvcTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요"))
                .andDo(print()); //http 요청에 대한 summary 남겨줌
    }
}