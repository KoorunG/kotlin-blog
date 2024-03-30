package org.koorung.kotlinblog.controller

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.koorung.kotlinblog.request.PostCreate
import org.koorung.kotlinblog.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @GetMapping
    fun get() = "hello world"

    @PostMapping
    fun post(@Valid @RequestBody request: PostCreate) {
        postService.write(request)
    }
}