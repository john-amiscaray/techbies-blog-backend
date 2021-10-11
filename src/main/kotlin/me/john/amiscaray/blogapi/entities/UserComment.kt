package me.john.amiscaray.blogapi.entities

import javax.persistence.*

@Entity
data class UserComment(
    @ManyToOne
    private val author: User,
    private val content: String,
    @ManyToOne
    private val blogPost: BlogPost
): BaseEntity(-1){

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