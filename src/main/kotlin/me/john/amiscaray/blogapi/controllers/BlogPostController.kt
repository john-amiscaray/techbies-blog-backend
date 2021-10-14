package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.annotations.ApiPageable
import me.john.amiscaray.blogapi.domain.BlogPostDto
import me.john.amiscaray.blogapi.services.BlogPostService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.net.URI

@Api(description = "Controller for managing blog posts")
@RestController
@RequestMapping("/blog")
class BlogPostController(private val blogPostService: BlogPostService,
    @Qualifier("notImplemented") private val notImplementedResponse: ResponseEntity<Any>) {

    @Value("\${app.origin}")
    private lateinit var origin: String

    private val logger = LoggerFactory.getLogger(BlogPostController::class.java)

    @ApiOperation(value = "post a blog post", notes = "If successful, returns 201 " +
            "response with path to get that new blog post")
    @PostMapping
    fun createBlogPost(@RequestBody blogPostDto: BlogPostDto): ResponseEntity<Void>{

        return try {
            val newPost = blogPostService.saveBlogPost(blogPostDto)
            ResponseEntity.created(URI("${origin}api/blog/post/${newPost.id}"))
                .build()
        }catch(ex: NoSuchElementException){
            ResponseEntity.internalServerError()
                .build()
        }

    }

    @ApiOperation(value = "edit a blog post | NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @PatchMapping("/post/{postId}")
    fun editBlogPost(@PathVariable("postId") id: Long, @RequestBody blogPost: BlogPostDto): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "delete a blog post | NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @DeleteMapping("/post/{postId}")
    fun deleteBlogPost(@PathVariable("postId") id: Long): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "get recent blog posts. Returns array of BlogPostDtos (see models below). " +
            "No authorization header required for this endpoint",
        notes = "You can set how many blog posts you want to retrieve via query parameter. " +
            "See query parameters in the docs for this endpoint. ")
    @ApiPageable
    @GetMapping("/recent")
    fun getRecent(@ApiIgnore pageable: Pageable): ResponseEntity<Set<BlogPostDto>>{

        return ResponseEntity.ok(blogPostService.getRecentPosts(pageable as PageRequest))
    }

    @ApiOperation(value = "get blog post by id. Returns BlogPostDto (see models below). " +
            "No authorization header required for this endpoint ")
    @GetMapping("/post/{postId}")
    fun getBlogPostById(@PathVariable("postId") id: Long): ResponseEntity<BlogPostDto>{

        return try{
            ResponseEntity.ok(blogPostService.getBlogPostById(id))
        }catch(ex: NoSuchElementException){
            ResponseEntity.notFound()
                .build()
        }

    }

}