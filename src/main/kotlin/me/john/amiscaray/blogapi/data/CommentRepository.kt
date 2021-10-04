package me.john.amiscaray.blogapi.data

import me.john.amiscaray.blogapi.entities.UserComment
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : PagingAndSortingRepository<UserComment, Long>