package me.john.amiscaray.blogapi.entities

import me.john.amiscaray.blogapi.domain.PublishedBlogPostDto
import me.john.amiscaray.blogapi.domain.UnpublishedBlogPostDto
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

    var wowReactions: Int = 0,
    var likeReactions: Int = 0,
    var worriedReactions: Int = 0,
    var sadReactions: Int = 0,
    var angryReactions: Int = 0,
    @Lob
    var coverImage: ByteArray? = null,

    @OneToMany(mappedBy = "blogPost")
    val comments: Set<UserComment> = HashSet(),

    @ManyToMany(mappedBy = "bookMarks")
    var bookMarkedBy: Set<User> = HashSet(),

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

    fun toUnpublishedBlogPostDto(): UnpublishedBlogPostDto{

        return UnpublishedBlogPostDto(id, title, content, tags)

    }

    fun toPublishedBlogPostDto(): PublishedBlogPostDto{

        val commentDtos = comments.map {
            it.toDto()
        }.toSet()
        return PublishedBlogPostDto(id, title, content, tags, commentDtos,
            wowReactions, likeReactions, sadReactions, angryReactions)

    }

}