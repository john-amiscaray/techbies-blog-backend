package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.BlogPostDto
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.domain.ReactionRequest
import me.john.amiscaray.blogapi.entities.BlogPost
import org.springframework.data.domain.PageRequest
import java.awt.print.Pageable
import javax.xml.stream.events.Comment

interface BlogPostService {

    fun getBlogPostsOfUser(): Set<BlogPostDto>

    fun getBookMarksOfUser(): Set<BlogPostDto>

    fun processReactionRequest(reactionRequest: ReactionRequest)

    fun processBookmarkRequest(bookMarkRequest: BookmarkRequest)

    fun commentOnPost(blogPostId: Long, comment: CommentDto)

    fun getCommentsOnPost(blogPostId: Long): Set<Comment>

    fun getUserFeed(): Set<BlogPost>

    fun saveBlogPost(blogPost: BlogPostDto): BlogPost

    fun deleteBlogPost(id: Long)

    fun editBlogPost(id: Long, blogPost: BlogPostDto)

    fun getBlogPostById(id: Long): BlogPostDto

    fun getRecentPosts(pageable: PageRequest): Set<BlogPostDto>

}