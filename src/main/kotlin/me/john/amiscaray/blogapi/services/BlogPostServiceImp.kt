package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.BlogPostRepository
import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.BlogPostDto
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.domain.ReactionRequest
import me.john.amiscaray.blogapi.entities.BlogPost
import org.springframework.stereotype.Service
import javax.xml.stream.events.Comment

@Service
class BlogPostServiceImp(private val blogPostRepo: BlogPostRepository, private val userService: UserService,
                         private val userRepo: UserRepository) : BlogPostService {
    override fun getBlogPostsOfUser(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun getBookMarksOfUser(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun processReactionRequest(reactionRequest: ReactionRequest) {
        TODO("Not yet implemented")
    }

    override fun processBookmarkRequest(bookMarkRequest: BookmarkRequest) {
        TODO("Not yet implemented")
    }

    override fun commentOnPost(userId: Long, blogPostId: Long, comment: CommentDto) {
        TODO("Not yet implemented")
    }

    override fun getCommentsOnPost(blogPostId: Long): Set<Comment> {
        TODO("Not yet implemented")
    }

    override fun getUserFeed(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun saveBlogPost(blogPost: BlogPostDto): BlogPost {
        val author = userService.getCurrentlySignedInUser()
        val blogPostEntity = BlogPost(id=-1,
            title=blogPost.title,
            content=blogPost.content,
            tags=blogPost.tags,
            author=author
        )
        return blogPostRepo.save(blogPostEntity)
    }

    override fun deleteBlogPost(id: Long) {
        TODO("Not yet implemented")
    }

    override fun editBlogPost(id: Long, blogPost: BlogPostDto) {
        TODO("Not yet implemented")
    }
}