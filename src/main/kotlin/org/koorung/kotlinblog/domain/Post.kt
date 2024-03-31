package org.koorung.kotlinblog.domain

import jakarta.persistence.*

@Entity
class Post(
    title: String,
    content: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column
    var title = title
        protected set

    @Lob
    @Column
    var content = content
        protected set
}