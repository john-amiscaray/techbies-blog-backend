package me.john.amiscaray.blogapi.data

import me.john.amiscaray.blogapi.domain.UserReactionRecordPK
import me.john.amiscaray.blogapi.entities.UserReactionRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReactionRecordRepository: JpaRepository<UserReactionRecord, UserReactionRecordPK>