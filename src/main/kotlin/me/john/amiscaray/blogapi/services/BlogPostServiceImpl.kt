package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.BlogPostRepository
import me.john.amiscaray.blogapi.data.TagRepo
import me.john.amiscaray.blogapi.domain.PublishedBlogPostDto
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
                          private val userService: UserService, private val tagRepo: TagRepo) : BlogPostService {
    override fun getBlogPostsOfUser(): Set<PublishedBlogPostDto> {
        return blogPostRepo.findAllByAuthor(userService.getCurrentlySignedInUser())
            .map {
                it.toPublishedBlogPostDto()
            }.toSet()
    }

    override fun saveBlogPost(blogPost: UnpublishedBlogPostDto): BlogPost {
        val author = userService.getCurrentlySignedInUser()
        val errorReason = "Missing blog info"
        val tags = blogPost.tags?.let { BlogPost.stringToTags(it) } ?: throw TechbiesBadRequestException("Tags are required")
        for(tag in tags){
            if(!tagRepo.existsById(tag.name)){
                tagRepo.save(tag)
            }
        }
        val blogPostEntity = BlogPost(
            title=blogPost.title ?: throw TechbiesBadRequestException(errorReason),
            content=blogPost.content ?: throw TechbiesBadRequestException(errorReason),
            tags= tags,
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
        val tags = blogPost.tags?.let { BlogPost.stringToTags(it) }
        if(tags != null){
            for(tag in tags){
                if(!tagRepo.existsById(tag.name)){
                    tagRepo.save(tag)
                }
            }
        }
        val blogPostEntity = BlogPost(
            title=blogPost.title ?: originalPost.title,
            content=blogPost.content ?: originalPost.content,
            tags= tags ?: originalPost.tags,
            author=user,
            timePosted=Timestamp(System.currentTimeMillis())
        )
        blogPostEntity.id=id
        blogPostRepo.save(blogPostEntity)

    }

    override fun getBlogPostById(id: Long): PublishedBlogPostDto {
        val blogPost = blogPostRepo.findById(id).orElseThrow()
        return blogPost.toPublishedBlogPostDto()
    }

    override fun getRecentPosts(pageable: PageRequest): Set<PublishedBlogPostDto> {
        val recentPageable = PageRequest.of(pageable.pageNumber,
            pageable.pageSize, Sort.by(Sort.Direction.DESC, "timePosted"))
        return blogPostRepo.findAll(recentPageable).map {
            it.toPublishedBlogPostDto()
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