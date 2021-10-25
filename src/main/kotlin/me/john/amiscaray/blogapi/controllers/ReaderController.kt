package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.ReactionRequest
import me.john.amiscaray.blogapi.exceptions.TechbiesBlogPostNotFoundException
import me.john.amiscaray.blogapi.services.UserActionService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(description = "A controller for managing reader actions")
@RestController
@RequestMapping("/reader")
class ReaderController(private val userActionService: UserActionService,
                       @Qualifier("notImplemented") private val notImplementedResponse: ResponseEntity<Any>) {

    @ApiOperation(value = "add or remove a bookmark | NOT IMPLEMENTED")
    @PostMapping("/bookmark")
    fun receiveBookmarkRequest(@RequestBody bookmarkRequest: BookmarkRequest): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "Get user feed. Returns array of BlogPostDtos (see models below) | NOT IMPLEMENTED")
    @GetMapping("/feed")
    fun getUserFeed(): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "Add or remove a reaction", notes = "Request body is a ReactionRequest (see models below).")
    @PostMapping("/reaction")
    fun receiveReactionRequest(@RequestBody reactionRequest: ReactionRequest): ResponseEntity<String>{

        return try{
            userActionService.processReactionRequest(reactionRequest)
            ResponseEntity.ok("Successfully processed reaction request")
        }catch(ex: TechbiesBlogPostNotFoundException){
            ResponseEntity
                .notFound().build()
        }

    }

    @ApiOperation(value = "comment on blog post | NOT IMPLEMENTED", notes = "comment on a post, " +
            "given the id of the post in the URL, and a CommentDto as the request body.")
    @PostMapping("/comment/on/post/{postId}")
    fun commentOnBlogPost(@PathVariable("postId") postId: Long): ResponseEntity<Any>{

        return notImplementedResponse

    }



}