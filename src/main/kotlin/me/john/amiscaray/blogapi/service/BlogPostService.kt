package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.BlogPostDto
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.domain.ReactionRequest
import me.john.amiscaray.blogapi.entities.BlogPost
import javax.xml.stream.events.Comment

interface BlogPostService {

    fun getBlogPostsOfUser(userId: Long): Set<BlogPost>

    fun getBookMarksOfUser(userId: Long): Set<BlogPost>

    fun processReactionRequest(reactionRequest: ReactionRequest)

    fun processBookmarkRequest(bookMarkRequest: BookmarkRequest)

    fun commentOnPost(userId: Long, blogPostId: Long, comment: CommentDto)

    fun getCommentsOnPost(blogPostId: Long): Set<Comment>

    fun getUserFeed(userId: Long): Set<BlogPost>

    fun saveBlogPost(blogPost: BlogPostDto)

    fun deleteBlogPost(id: Long)

    fun editBlogPost(id: Long, blogPost: BlogPostDto)

}