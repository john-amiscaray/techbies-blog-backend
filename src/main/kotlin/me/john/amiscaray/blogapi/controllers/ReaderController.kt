package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.BookmarkRequest
import me.john.amiscaray.blogapi.domain.CommentDto
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

    @ApiOperation(value = "Add or remove a bookmark", notes = "Request body is a BookmarkRequest (see models below).")
    @PostMapping("/bookmark")
    fun receiveBookmarkRequest(@RequestBody bookmarkRequest: BookmarkRequest): ResponseEntity<String>{

        userActionService.processBookmarkRequest(bookmarkRequest)
        return ResponseEntity.ok("Successfully processed request")

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

    @ApiOperation(value = "Comment on blog post", notes = "Comment on a post, " +
            "given a CommentDto as the request body (see models below).")
    @PostMapping("/comment")
    fun commentOnBlogPost(@RequestBody comment: CommentDto): ResponseEntity<Void>{

        userActionService.commentOnPost(comment)
        return ResponseEntity.noContent()
            .build()

    }

    @ApiOperation(value = "Delete blog post by id")
    @DeleteMapping("/comment/{commentId}")
    fun deleteCommentById(@PathVariable("commentId") commentId: Long): ResponseEntity<Void>{

        userActionService.deleteCommentOnPost(commentId)

        return ResponseEntity
            .noContent()
            .build()

    }



}