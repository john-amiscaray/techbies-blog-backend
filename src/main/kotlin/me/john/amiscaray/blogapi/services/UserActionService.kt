package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.*
import me.john.amiscaray.blogapi.entities.BlogPost

interface UserActionService {

    fun processReactionRequest(reactionRequest: ReactionRequest)

    fun processBookmarkRequest(bookMarkRequest: BookmarkRequest)

    fun commentOnPost(blogPostId: Long, comment: CommentDto)

    fun getBookMarksOfUser(): Set<UnpublishedBlogPostDto>

    fun getUserFeed(): Set<PublishedBlogPostDto>

}