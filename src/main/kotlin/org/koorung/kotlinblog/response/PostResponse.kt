package org.koorung.kotlinblog.response

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
) {
//    init {
//        // 읽어온 글 제목은 최대 10글자까지 넘어옴
//        title = title.substring(0, 10)
//    }
}