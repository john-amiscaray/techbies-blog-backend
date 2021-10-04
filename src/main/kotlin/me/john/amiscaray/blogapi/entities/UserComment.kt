package me.john.amiscaray.blogapi.entities

import javax.persistence.*

@Entity
data class UserComment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long,
    @ManyToOne
    private val author: User,
    private val content: String,
    @ManyToOne
    private val blogPost: BlogPost
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserComment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}