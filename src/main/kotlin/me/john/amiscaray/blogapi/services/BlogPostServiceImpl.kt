package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.BlogPostRepository
import me.john.amiscaray.blogapi.domain.UnpublishedBlogPostDto
import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.entities.User
import me.john.amiscaray.blogapi.exceptions.TechbiesBadRequestException
import me.john.amiscaray.blogapi.exceptions.TechbiesBlogPostNotFoundException
import me.john.amiscaray.blogapi.exceptions.TechbiesIllegalBlogAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.xml.stream.events.Comment

@Service
class BlogPostServiceImpl(private val blogPostRepo: BlogPostRepository,
                          private val userService: UserService) : BlogPostService {
    override fun getBlogPostsOfUser(): Set<UnpublishedBlogPostDto> {
        return blogPostRepo.findAllByAuthor(userService.getCurrentlySignedInUser())
            .map {
                it.toDto()
            }.toSet()
    }

    override fun getCommentsOnPost(blogPostId: Long): Set<Comment> {
        TODO("Not yet implemented")
    }

    override fun getUserFeed(): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun saveBlogPost(blogPost: UnpublishedBlogPostDto): BlogPost {
        val author = userService.getCurrentlySignedInUser()
        val errorReason = "Missing blog info"
        val blogPostEntity = BlogPost(
            title=blogPost.title ?: throw TechbiesBadRequestException(errorReason),
            content=blogPost.content ?: throw TechbiesBadRequestException(errorReason),
            tags=blogPost.tags ?: throw TechbiesBadRequestException(errorReason),
            author=author,
            timePosted=Timestamp(System.currentTimeMillis())
        )
        return blogPostRepo.save(blogPostEntity)
    }

    override fun deleteBlogPost(id: Long) {
        if(!blogPostRepo.existsById(id)){
            throw TechbiesBlogPostNotFoundException()
        }
        checkUserIsOwnerOrThrow(id)
        blogPostRepo.deleteById(id)
    }

    override fun editBlogPost(id: Long, blogPost: UnpublishedBlogPostDto) {

        blogPostExistsOrThrow(id)
        val originalPost = blogPostRepo.findById(id).orElseThrow()
        val user = checkUserIsOwnerOrThrow(id)
        val blogPostEntity = BlogPost(
            title=blogPost.title ?: originalPost.title,
            content=blogPost.content ?: originalPost.content,
            tags=blogPost.tags ?: originalPost.tags,
            author=user,
            timePosted=Timestamp(System.currentTimeMillis())
        )
        blogPostEntity.id=id
        blogPostRepo.save(blogPostEntity)

    }

    override fun getBlogPostById(id: Long): UnpublishedBlogPostDto {
        val blogPost = blogPostRepo.findById(id).orElseThrow()
        return UnpublishedBlogPostDto(blogPost.id, blogPost.title, blogPost.content, blogPost.tags)
    }

    override fun getRecentPosts(pageable: PageRequest): Set<UnpublishedBlogPostDto> {
        val recentPageable = PageRequest.of(pageable.pageNumber,
            pageable.pageSize, Sort.by(Sort.Direction.DESC, "timePosted"))
        return blogPostRepo.findAll(recentPageable).map {
            it.toDto()
        }.toSet()
    }

    override fun checkUserIsOwnerOrThrow(blogPostId: Long): User {

        val user = userService.getCurrentlySignedInUser()
        if(!blogPostRepo.existsByIdAndAuthor(blogPostId, user)){
            throw TechbiesIllegalBlogAccessException()
        }
        return user
    }

    override fun blogPostExistsOrThrow(blogPostId: Long) {
        if(!blogPostRepo.existsById(blogPostId)){
            throw TechbiesBlogPostNotFoundException()
        }
    }

}