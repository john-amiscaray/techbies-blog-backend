package me.john.amiscaray.blogapi.entities

import javax.persistence.*


// TODO : Add cover image field
// TODO : Add read by field (many to many)
// TODO : Change likes to separate fields for different reactions
// TODO : Add time created as field
@Entity
data class BlogPost(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val title: String,
    val content: String,
    val tags: String,

    @ManyToOne
    val author: User,

    val wowReactions: Int = 0,
    val likeReactions: Int = 0,
    val worriedReactions: Int = 0,
    val sadReactions: Int = 0,
    val angryReactions: Int = 0,
    @Lob
    val coverImage: ByteArray? = null,

    @OneToMany(mappedBy = "blogPost")
    val comments: Set<UserComment> = emptySet(),

    @ManyToMany(mappedBy = "bookMarks")
    val bookMarkedBy: Set<User> = emptySet()

){

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
}