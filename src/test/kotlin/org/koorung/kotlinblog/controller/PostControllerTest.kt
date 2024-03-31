package org.koorung.kotlinblog.controller

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koorung.kotlinblog.domain.Post
import org.koorung.kotlinblog.repository.PostRepository
import org.koorung.kotlinblog.request.PostCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val postRepository: PostRepository,
) {

    @BeforeEach
    fun cleanup() {
        postRepository.deleteAllInBatch()
    }

    @Test
    fun `post 요청 시 글을 등록한다`() {
        mockMvc.post("/posts") {
            contentType = APPLICATION_JSON
            content = Json.encodeToString(
                PostCreate(
                    title = "제목입니다...",
                    content = "내용입니다.."
                )
            )
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }


    @Test
    fun `post 요청 시 title은 필수다`() {
        mockMvc.post("/posts") {
            contentType = APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "         ", content = "내용입니다..."))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(1) } }
            .andExpect { jsonPath("$.validation.title") { value("제목은 빈 값이 올 수 없습니다!") } }
            .andDo { print() }
    }

    @Test
    fun `post 요청 시 content은 필수다`() {
        mockMvc.post("/posts") {
            contentType = APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "글제목", content = "       "))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(1) } }
            .andExpect {
                jsonPath("$.validation.content") {
                    value("글내용은 빈 값이 올 수 없습니다!")
                }
            }
            .andDo { print() }
    }

    @Test
    fun `fieldError가 2개 이상인 경우`() {
        mockMvc.post("/posts") {
            contentType = APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "        ", content = "       "))
        }.andExpect { status { isBadRequest() } }
            .andExpect { jsonPath("$.count") { value(2) } }
            .andExpect { jsonPath("$.code") { value(HttpStatus.BAD_REQUEST.value()) } }
            .andExpect { jsonPath("$.message") { value("잘못된 요청입니다!") } }
            .andExpect { jsonPath("$.validation.title") { exists() } }
            .andExpect { jsonPath("$.validation.content") { exists() } }
//            .andExpect { jsonPath("$.results[0].message") { value("글내용은 빈 값이 올 수 없습니다!")} }
            .andDo { print() }
    }

    @Test
    fun `post 요청 시 DB에 값이 저장된다`() {
        // when
        mockMvc.post("/posts") {
            contentType = APPLICATION_JSON
            content = Json.encodeToString(PostCreate(title = "제목입니다.", content = "내용입니다!!"))
        }.andExpect { status { isOk() } }
            .andDo { print() }

        // then
        assertThat(postRepository.count()).isEqualTo(1L)
        assertThat(postRepository.findAll().first()).extracting { it.title }.isEqualTo("제목입니다.")
        assertThat(postRepository.findAll().first()).extracting { it.content }.isEqualTo("내용입니다!!")
    }

    @Test
    fun `get 요청 시 DB의 값을 불러온다`() {
        // given
        val save = postRepository.save(
            Post(
                title = "테스트제목",
                content = "테스트내용",
            )
        )

        // when && then
        mockMvc.get("/posts/${save.id}") {
            contentType = APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { jsonPath("$.id") { value(save.id)} } }
            .andExpect { content { jsonPath("$.title") { value("테스트제목")} } }
            .andExpect { content { jsonPath("$.content") { value("테스트내용")} } }
            .andDo { print() }
    }
}