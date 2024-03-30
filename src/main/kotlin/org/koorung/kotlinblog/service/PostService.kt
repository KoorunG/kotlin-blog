package org.koorung.kotlinblog.service

import org.koorung.kotlinblog.domain.Post
import org.koorung.kotlinblog.repository.PostRepository
import org.koorung.kotlinblog.request.PostCreate
import org.koorung.kotlinblog.utils.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {
    val logger = logger()

    @Transactional
    fun write(postCreate: PostCreate) {
        val post = Post(postCreate.title, postCreate.content)
        logger.info { "post :: $post" }
        postRepository.save(post)
    }
}