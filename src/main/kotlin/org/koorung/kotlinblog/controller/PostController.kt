package org.koorung.kotlinblog.controller

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.koorung.kotlinblog.request.PostCreate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController {

    val kLogger: KLogger = KotlinLogging.logger { }

    @GetMapping
    fun get() = "hello world"

    @PostMapping
    fun post(@Valid @RequestBody params: PostCreate) {
        kLogger.info { "params ::: $params" }
    }
}