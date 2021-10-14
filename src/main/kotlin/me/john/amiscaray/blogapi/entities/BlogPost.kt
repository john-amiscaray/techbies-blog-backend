package me.john.amiscaray.blogapi.entities

import me.john.amiscaray.blogapi.domain.BlogPostDto
import java.sql.Timestamp
import javax.persistence.*

// TODO : Change likes to separate fields for different reactions
// TODO : Add time created as field
@Entity
data class BlogPost(

    val title: String,
    val content: String,
    val tags: String,

    @ManyToOne
    val author: User,

    val timePosted: Timestamp,

    val wowReactions: Int = 0,
    val likeReactions: Int = 0,
    val worriedReactions: Int = 0,
    val sadReactions: Int = 0,
    val angryReactions: Int = 0,
    @Lob
    val coverImage: ByteArray? = null,

    @OneToMany(mappedBy = "blogPost")
    val comments: Set<UserComment> = HashSet(),

    @ManyToMany(mappedBy = "bookMarks")
    val bookMarkedBy: Set<User> = HashSet(),

    @ManyToMany(mappedBy = "blogPostsRead")
    val readBy: Set<User> = HashSet()

): BaseEntity(-1) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BlogPost

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun toDto(): BlogPostDto{

        return BlogPostDto(id, title, content, tags)

    }

}