package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.UnpublishedBlogPostDto
import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.entities.User
import org.springframework.data.domain.PageRequest
import javax.xml.stream.events.Comment

interface BlogPostService {

    fun getBlogPostsOfUser(): Set<UnpublishedBlogPostDto>

    fun getCommentsOnPost(blogPostId: Long): Set<Comment>

    fun getUserFeed(): Set<BlogPost>

    fun saveBlogPost(blogPost: UnpublishedBlogPostDto): BlogPost

    fun deleteBlogPost(id: Long)

    fun editBlogPost(id: Long, blogPost: UnpublishedBlogPostDto)

    fun getBlogPostById(id: Long): UnpublishedBlogPostDto

    fun getRecentPosts(pageable: PageRequest): Set<UnpublishedBlogPostDto>

    fun checkUserIsOwnerOrThrow(blogPostId: Long): User

    fun blogPostExistsOrThrow(blogPostId: Long)

}