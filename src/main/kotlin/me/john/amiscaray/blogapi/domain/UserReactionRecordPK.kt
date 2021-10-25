package me.john.amiscaray.blogapi.domain

import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.entities.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Embeddable
data class UserReactionRecordPK(
    @OneToOne
    private val user: User,
    @ManyToOne
    private val blogPost: BlogPost
): Serializable