package org.koorung.kotlinblog.domain

import jakarta.persistence.*

@Entity
class Post(
    title: String,
    content: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    @Column
    var title = title
        protected set

    @Lob
    @Column
    var content = content
        protected set
}