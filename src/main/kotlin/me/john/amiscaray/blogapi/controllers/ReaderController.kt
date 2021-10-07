package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.ReactionRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(description = "A controller for managing reader actions")
@RestController
@RequestMapping("/reader")
class ReaderController(@Qualifier("notImplemented") private val notImplementedResponse: ResponseEntity<Any>) {

    @ApiOperation(value = "add or remove a bookmark | NOT IMPLEMENTED")
    @PostMapping("/bookmark")
    fun receiveBookmarkRequest(@RequestBody bookmarkRequest: BookmarkRequest): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "get user feed. Returns array of BlogPostDtos (see models below) | NOT IMPLEMENTED")
    @GetMapping("/feed")
    fun getUserFeed(): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "add or remove a reaction | NOT IMPLEMENTED")
    @PostMapping("/reaction")
    fun receiveReactionRequest(@RequestBody reactionRequest: ReactionRequest): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "comment on blog post | NOT IMPLEMENTED", notes = "comment on a post, " +
            "given the id of the post in the URL, and a CommentDto as the request body.")
    @PostMapping("/comment/on/post/{postId}")
    fun commentOnBlogPost(@PathVariable("postId") postId: Long): ResponseEntity<Any>{

        return notImplementedResponse

    }



}