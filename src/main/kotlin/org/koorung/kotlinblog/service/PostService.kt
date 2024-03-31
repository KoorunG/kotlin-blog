package org.koorung.kotlinblog.service

import org.koorung.kotlinblog.domain.Post
import org.koorung.kotlinblog.repository.PostRepository
import org.koorung.kotlinblog.request.PostCreate
import org.koorung.kotlinblog.utils.logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    val logger = logger()

    @Transactional
    fun write(postCreate: PostCreate): Post {
        val post = Post(
            title = postCreate.title,
            content = postCreate.content
        )
        logger.info { "post :: $post" }
        return postRepository.save(post)
    }

    fun get(id: Long) =
        postRepository.findByIdOrNull(id) ?: throw NoSuchElementException("해당 글이 존재하지 않습니다!!, id :: $id")
}