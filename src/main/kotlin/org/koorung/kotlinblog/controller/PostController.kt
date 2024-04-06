package org.koorung.kotlinblog.controller

import jakarta.validation.Valid
import org.koorung.kotlinblog.request.PostCreate
import org.koorung.kotlinblog.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
) {
    @PostMapping
    fun savePost(@Valid @RequestBody request: PostCreate): Long {
        val post = postService.write(request)
        // 일단 응답으로 PK를 내려주도록 한다...
        return post.id
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long) = postService.get(postId)

    @GetMapping()
    fun getPosts() = postService.getAll()
}