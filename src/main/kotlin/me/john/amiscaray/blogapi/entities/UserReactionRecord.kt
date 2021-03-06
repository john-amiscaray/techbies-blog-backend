package me.john.amiscaray.blogapi.entities

import me.john.amiscaray.blogapi.domain.UserReactionRecordPK
import java.io.Serializable
import javax.persistence.EmbeddedId
import javax.persistence.Entity


@Entity
data class UserReactionRecord(
    @EmbeddedId
    var id: UserReactionRecordPK,
    var hasWow: Boolean=false,
    var hasLike: Boolean=false,
    var hasWorried: Boolean=false,
    var hasSad: Boolean=false,
    var hasAngry: Boolean=false
): Serializable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserReactionRecord

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}