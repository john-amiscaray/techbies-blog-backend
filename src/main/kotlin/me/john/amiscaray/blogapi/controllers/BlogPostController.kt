package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.BlogPostDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(description = "Controller for managing blog posts")
@RestController
@RequestMapping("/blog")
class BlogPostController(@Qualifier("notImplemented") private val notImplementedResponse: ResponseEntity<Any>) {

    @ApiOperation(value = "post a blog post | NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @PostMapping
    fun createBlogPost(@RequestBody blogPostDto: BlogPostDto): ResponseEntity<Any>{

        return notImplementedResponse

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

    @ApiOperation(value = "get recent blog posts. Returns array of BlogPostDtos (see models below) " +
            "| NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @GetMapping("/recent")
    fun getRecent(pageable: Pageable): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "get new blog posts. Returns array of BlogPostDtos (see models below) " +
            "| NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @GetMapping("/new")
    fun getNew(pageable: Pageable): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "get blog post by id. Returns BlogPostDto (see models below) " +
            "| NOT IMPLEMENTED", notes = "NOT IMPLEMENTED")
    @GetMapping("/post/{postId}")
    fun getBlogPostById(@PathVariable("postId") id: Long): ResponseEntity<Any>{

        return notImplementedResponse

    }

}