package org.koorung.kotlinblog.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koorung.kotlinblog.domain.Post
import org.koorung.kotlinblog.repository.PostRepository
import org.koorung.kotlinblog.request.PostCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

@SpringBootTest
class PostServiceTest @Autowired constructor(
    private val postService: PostService,
    private val postRepository: PostRepository,
) {
    @BeforeEach
    fun cleanUp() {
        postRepository.deleteAllInBatch()
    }
    @Test
    fun `글 작성`() {
        // given
        val postCreate = PostCreate(
            title = "제목입니다!",
            content = "내용입니다.."
        )
        // when
        val post = postService.write(postCreate)

        // then
        assertThat(postRepository.count()).isEqualTo(1)
        assertThat(post).extracting { post.title }.isEqualTo("제목입니다!")
        assertThat(post).extracting { post.content }.isEqualTo("내용입니다..")
    }

    @Test
    fun `글 조회`() {
        // given
        val save = postRepository.save(
            Post(
                title = "테스트제목",
                content = "테스트내용",
            )
        )

        // when
        val post = postService.get(save.id)

        // then
        assertThat(post).isNotNull
        assertThat(post).extracting { post.title }.isEqualTo("테스트제목")
        assertThat(post).extracting { post.content }.isEqualTo("테스트내용")
        assertThat(post).extracting { post.id }.isEqualTo(save.id)
    }
}