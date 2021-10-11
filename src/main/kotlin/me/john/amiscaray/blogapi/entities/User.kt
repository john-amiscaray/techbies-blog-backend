package me.john.amiscaray.blogapi.entities

import javax.persistence.*

@Entity
data class User(
    @Column(unique = true)
    val email: String,
    val password: String,

    var accountActivated: Boolean = false,

    @OneToMany(mappedBy = "author")
    private val blogPosts: Set<BlogPost> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "blog_posts_bookmarked",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "blog_post_id")]
    )
    private val bookMarks: Set<BlogPost> = HashSet(),

    @OneToMany(mappedBy = "author")
    private val comments: Set<UserComment> = HashSet()

): BaseEntity(-1){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}