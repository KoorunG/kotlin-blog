package org.koorung.kotlinblog.request

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class PostCreate(
    // Kotlin에서는 annotation이 어디 붙을 지 명시를 해주어야 제대로 동작하는 경우가 있다.
    // @Valid의 경우 field에 붙어있음을 명시해주어야함...!
    @field:NotBlank(message = "제목은 빈 값이 올 수 없습니다!")
    val title: String,

    @field:NotBlank(message = "글내용은 빈 값이 올 수 없습니다!")
    val content: String
)