package org.koorung.kotlinblog.repository

import org.koorung.kotlinblog.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>