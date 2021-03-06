package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.annotations.ApiPageable
import me.john.amiscaray.blogapi.domain.PublishedBlogPostDto
import me.john.amiscaray.blogapi.domain.UnpublishedBlogPostDto
import me.john.amiscaray.blogapi.exceptions.TechbiesBadRequestException
import me.john.amiscaray.blogapi.exceptions.TechbiesBlogPostNotFoundException
import me.john.amiscaray.blogapi.exceptions.TechbiesIllegalBlogAccessException
import me.john.amiscaray.blogapi.services.BlogPostService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.net.URI

@Api(description = "Controller for managing blog posts")
@RestController
@RequestMapping("/blog")
class BlogPostController(private val blogPostService: BlogPostService) {

    @Value("\${app.origin}")
    private lateinit var origin: String

    @ApiOperation(value = "post a blog post", notes = "If successful, returns 201 " +
            "response with path to get that new blog post")
    @PostMapping
    fun createBlogPost(@RequestBody blogPostDto: UnpublishedBlogPostDto): ResponseEntity<Any>{

        return try {
            val newPost = blogPostService.saveBlogPost(blogPostDto)
            ResponseEntity.created(URI("${origin}api/blog/post/${newPost.id}"))
                .build()
        }catch(ex: NoSuchElementException){
            ResponseEntity.internalServerError()
                .build()
        }catch(ex: TechbiesBadRequestException){
            ResponseEntity.badRequest()
                .body(ex.message)
        }

    }

    @ApiOperation(value = "edit a blog post")
    @PatchMapping("/post/{postId}")
    fun editBlogPost(@PathVariable("postId") id: Long, @RequestBody blogPost: UnpublishedBlogPostDto): ResponseEntity<Any>{

        return try{
            blogPostService.editBlogPost(id, blogPost)
            ResponseEntity
                .ok("Updated blog post successfully")
        }catch (ex: TechbiesIllegalBlogAccessException){
            ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.message)
        }

    }

    @ApiOperation(value = "delete a blog post")
    @DeleteMapping("/post/{postId}")
    fun deleteBlogPost(@PathVariable("postId") id: Long): ResponseEntity<Any>{

        return try{
            blogPostService.deleteBlogPost(id)
            ResponseEntity.noContent().build()
        }catch (ex: TechbiesBlogPostNotFoundException){
            ResponseEntity
                .notFound()
                .build()
        }catch (ex: TechbiesIllegalBlogAccessException){
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ex.message)
        }

    }

    @ApiOperation(value = "get recent blog posts. Returns array of PublishedBlogPostDtos (see models below). " +
            "No authorization header required for this endpoint.",
        notes = "You can set how many blog posts you want to retrieve via query parameter. " +
            "See query parameters in the docs for this endpoint. ")
    @ApiPageable
    @GetMapping("/public/recent")
    fun getRecent(@ApiIgnore pageable: Pageable): ResponseEntity<Set<PublishedBlogPostDto>>{

        return ResponseEntity.ok(blogPostService.getRecentPosts(pageable as PageRequest))
    }

    @ApiOperation(value = "get blog post by id. Returns UnpublishedBlogPostDto (see models below). " +
            "No authorization header required for this endpoint ")
    @GetMapping("/public/post/{postId}")
    fun getBlogPostById(@PathVariable("postId") id: Long): ResponseEntity<PublishedBlogPostDto>{

        return try{
            ResponseEntity.ok(blogPostService.getBlogPostById(id))
        }catch(ex: NoSuchElementException){
            ResponseEntity.notFound()
                .build()
        }

    }

    @ApiOperation(value = "Get all the blog posts for the logged in user." +
            " Returns array of PublishedBlogPostDtos (see models below).")
    @GetMapping("/your-posts")
    fun getYourPosts(): ResponseEntity<Set<PublishedBlogPostDto>>{

        return ResponseEntity.ok(blogPostService.getBlogPostsOfUser())

    }

}