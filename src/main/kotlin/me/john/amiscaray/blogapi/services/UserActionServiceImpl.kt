package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.UnpublishedBlogPostDto
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.domain.ReactionRequest

class UserActionServiceImpl: UserActionService {
    override fun processReactionRequest(reactionRequest: ReactionRequest) {
        TODO("Not yet implemented")
    }

    override fun processBookmarkRequest(bookMarkRequest: BookmarkRequest) {
        TODO("Not yet implemented")
    }

    override fun commentOnPost(blogPostId: Long, comment: CommentDto) {
        TODO("Not yet implemented")
    }

    override fun getBookMarksOfUser(): Set<UnpublishedBlogPostDto> {
        TODO("Not yet implemented")
    }
}