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
    private val id: Long,
    private val title: String,
    private val content: String,
    private val wowReactions: Int,
    private val likeReactions: Int,
    private val worriedReactions: Int,
    private val sadReactions: Int,
    private val angryReactions: Int,
    private val tags: String,

    @Lob
    private val coverImage: ByteArray,

    @ManyToOne
    private val author: User,

    @OneToMany(mappedBy = "blogPost")
    private val comments: Set<UserComment>,

    @ManyToMany(mappedBy = "bookMarks")
    private val bookMarkedBy: Set<User>

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