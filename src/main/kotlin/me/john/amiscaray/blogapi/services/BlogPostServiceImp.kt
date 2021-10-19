package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.BlogPostRepository
import me.john.amiscaray.blogapi.domain.BlogPostDto
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.domain.ReactionRequest
import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.exceptions.TechbiesBlogPostNotFoundException
import me.john.amiscaray.blogapi.exceptions.TechbiesIllegalBlogAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.xml.stream.events.Comment

@Service
class BlogPostServiceImp(private val blogPostRepo: BlogPostRepository,
                         private val userService: UserService) : BlogPostService {
    override fun getBlogPostsOfUser(): Set<BlogPostDto> {
        TODO("Not yet implemented")
    }

    override fun getBookMarksOfUser(): Set<BlogPostDto> {
        TODO("Not yet implemented")
    }

    override fun processReactionRequest(reactionRequest: ReactionRequest) {
        TODO("Not yet implemented")
    }

    override fun processBookmarkRequest(bookMarkRequest: BookmarkRequest) {
        TODO("Not yet implemented")
    }

    override fun commentOnPost(blogPostId: Long, comment: CommentDto) {
        TODO("Not yet implemented")
    }

    override fun getCommentsOnPost(blogPostId: Long): Set<Comment> {
        TODO("Not yet implemented")
    }

    override fun getUserFeed(): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun saveBlogPost(blogPost: BlogPostDto): BlogPost {
        val author = userService.getCurrentlySignedInUser()
        val blogPostEntity = BlogPost(
            title=blogPost.title,
            content=blogPost.content,
            tags=blogPost.tags,
            author=author,
            timePosted=Timestamp(System.currentTimeMillis())
        )
        return blogPostRepo.save(blogPostEntity)
    }

    override fun deleteBlogPost(id: Long) {
        if(!blogPostRepo.existsById(id)){
            throw TechbiesBlogPostNotFoundException()
        }
        val user = userService.getCurrentlySignedInUser()
        if(!blogPostRepo.existsByIdAndAuthor(id, user)){
            throw TechbiesIllegalBlogAccessException()
        }
        blogPostRepo.deleteById(id)
    }

    override fun editBlogPost(id: Long, blogPost: BlogPostDto) {
        TODO("Not yet implemented")
    }

    override fun getBlogPostById(id: Long): BlogPostDto {
        val blogPost = blogPostRepo.findById(id).orElseThrow()
        return BlogPostDto(blogPost.id, blogPost.title, blogPost.content, blogPost.tags)
    }

    override fun getRecentPosts(pageable: PageRequest): Set<BlogPostDto> {
        val recentPageable = PageRequest.of(pageable.pageNumber,
            pageable.pageSize, Sort.by(Sort.Direction.DESC, "timePosted"))
        return blogPostRepo.findAll(recentPageable).map {
            it.toDto()
        }.toSet()
    }
}