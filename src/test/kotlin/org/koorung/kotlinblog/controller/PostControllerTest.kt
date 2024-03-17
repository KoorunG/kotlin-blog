package org.koorung.kotlinblog.controller

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.koorung.kotlinblog.request.PostCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@WebMvcTest
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) {

    @Test
    fun `get 요청 시 hello world 출력`() {
        mockMvc.get("/posts")
            .andExpect { status { isOk() } }
            .andExpect { content { string("hello world") } }
            .andDo { print() }
    }

    @Test
    fun `post 요청 시 글을 등록한다`() {
        mockMvc.post("/posts") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(PostCreate("제목입니다...", "내용입니다.."))
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `post 요청 시 title은 필수다`() {
        mockMvc.post("/posts") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "         ", content = "내용입니다..."))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(1)} }
            .andExpect { jsonPath("$.validation.title") { value("제목은 빈 값이 올 수 없습니다!")} }
            .andDo { print() }
    }

    @Test
    fun `post 요청 시 content은 필수다`() {
        mockMvc.post("/posts") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "글제목", content = "       "))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(1)} }
            .andExpect { jsonPath("$.validation.content") { value("글내용은 빈 값이 올 수 없습니다!")} }
            .andDo { print() }
    }

    @Test
    fun `fieldError가 2개 이상인 경우`() {
        mockMvc.post("/posts") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "        ", content = "       "))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(2)} }
            .andExpect { jsonPath("$.code") { value(HttpStatus.BAD_REQUEST.value())} }
            .andExpect { jsonPath("$.message") { value("잘못된 요청입니다!")} }
            .andExpect { jsonPath("$.validation.title") { exists() } }
            .andExpect { jsonPath("$.validation.content") { exists() } }
//            .andExpect { jsonPath("$.results[0].message") { value("글내용은 빈 값이 올 수 없습니다!")} }
            .andDo { print() }
    }
}