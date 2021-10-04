package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.entities.BlogPost
import javax.xml.stream.events.Comment

interface BlogPostService {

    fun getBlogPostsOfUser(userId: Long): Set<BlogPost>

    fun getBookMarksOfUser(userId: Long): Set<BlogPost>

    fun likePost(userId: Long, blogPostId: Long)

    fun bookMarkPost(userId: Long, blogPostId: Long)

    fun commentOnPost(userId: Long, blogPostId: Long, comment: CommentDto)

    fun getCommentsOnPost(blogPostId: Long): Set<Comment>

    fun getUserFeed(userId: Long): Set<BlogPost>

}