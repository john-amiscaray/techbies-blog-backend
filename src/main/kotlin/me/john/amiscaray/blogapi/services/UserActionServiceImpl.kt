package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.BlogPostRepository
import me.john.amiscaray.blogapi.data.CommentRepository
import me.john.amiscaray.blogapi.data.ReactionRecordRepository
import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.*
import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.entities.UserComment
import me.john.amiscaray.blogapi.entities.UserReactionRecord
import me.john.amiscaray.blogapi.exceptions.TechbiesBadRequestException
import me.john.amiscaray.blogapi.exceptions.TechbiesBlogPostNotFoundException
import me.john.amiscaray.blogapi.exceptions.TechbiesCommentNotFoundException
import me.john.amiscaray.blogapi.exceptions.TechbiesIllegalCommentAccessException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserActionServiceImpl(
    private val blogPostRepo: BlogPostRepository,
    private val reactionRecordRepository: ReactionRecordRepository,
    private val userService: UserService,
    private val userRepo: UserRepository,
    private val commentRepo: CommentRepository
): UserActionService {

    override fun processReactionRequest(reactionRequest: ReactionRequest) {
        val post = blogPostRepo.findById(reactionRequest.postId).orElseThrow {
            TechbiesBlogPostNotFoundException()
        }
        val user = userService.getCurrentlySignedInUser()
        val reactionId = UserReactionRecordPK(user, post)
        val reactionRecord = if(!reactionRecordRepository.existsById(reactionId)){
            reactionRecordRepository.save(UserReactionRecord(id=reactionId))
        }else{
            reactionRecordRepository.getById(reactionId)
        }
        val badRemoveRequest = TechbiesBadRequestException("Cannot remove reaction that is not there")
        val badAddRequest = TechbiesBadRequestException("Cannot add reaction that's already there")
        val deltaReaction = if(reactionRequest.isRemoveReaction){
            when(reactionRequest.reaction){
                ReactionRequest.ReactionType.Angry -> {
                    if(!reactionRecord.hasAngry){
                        throw badRemoveRequest
                    }
                }
                ReactionRequest.ReactionType.Like -> {
                    if(!reactionRecord.hasLike){
                        throw badRemoveRequest
                    }
                }
                ReactionRequest.ReactionType.Sad -> {
                    if(!reactionRecord.hasSad){
                        throw badRemoveRequest
                    }
                }
                ReactionRequest.ReactionType.Worried -> {
                    if(!reactionRecord.hasWorried){
                        throw badRemoveRequest
                    }
                }
                ReactionRequest.ReactionType.Wow -> {
                    if(!reactionRecord.hasWow){
                        throw badRemoveRequest
                    }
                }
            }
            -1
        }else{
            when(reactionRequest.reaction){
                ReactionRequest.ReactionType.Angry -> {
                    if(reactionRecord.hasAngry){
                        throw badAddRequest
                    }
                }
                ReactionRequest.ReactionType.Like -> {
                    if(reactionRecord.hasLike){
                        throw badAddRequest
                    }
                }
                ReactionRequest.ReactionType.Sad -> {
                    if(reactionRecord.hasSad){
                        throw badAddRequest
                    }
                }
                ReactionRequest.ReactionType.Worried -> {
                    if(reactionRecord.hasWorried){
                        throw badAddRequest
                    }
                }
                ReactionRequest.ReactionType.Wow -> {
                    if(reactionRecord.hasWow){
                        throw badAddRequest
                    }
                }
            }
            1
        }
        when(reactionRequest.reaction){
            ReactionRequest.ReactionType.Angry -> {
                post.angryReactions += deltaReaction
                reactionRecord.hasAngry = !reactionRecord.hasAngry
            }
            ReactionRequest.ReactionType.Like -> {
                post.likeReactions += deltaReaction
                reactionRecord.hasLike = !reactionRecord.hasLike
            }
            ReactionRequest.ReactionType.Sad -> {
                post.sadReactions += deltaReaction
                reactionRecord.hasSad = !reactionRecord.hasSad
            }
            ReactionRequest.ReactionType.Worried -> {
                post.worriedReactions += deltaReaction
                reactionRecord.hasWorried = !reactionRecord.hasWorried
            }
            ReactionRequest.ReactionType.Wow -> {
                post.wowReactions += deltaReaction
                reactionRecord.hasWow = !reactionRecord.hasWow
            }
        }
        blogPostRepo.save(post)
        reactionRecordRepository.save(reactionRecord)
    }

    override fun processBookmarkRequest(bookMarkRequest: BookmarkRequest) {
        val user = userService.getCurrentlySignedInUser()
        val blogPost = blogPostRepo.findById(bookMarkRequest.blogPostId).orElseThrow()
        if(!bookMarkRequest.isRemoveBookmark){
            user.bookMarks = user.bookMarks.plus(blogPost)
            blogPost.bookMarkedBy = blogPost.bookMarkedBy.plus(user)
        }else{
            user.bookMarks = user.bookMarks.filter {
                it.id != bookMarkRequest.blogPostId
            }.toMutableSet()
            blogPost.bookMarkedBy = blogPost.bookMarkedBy.filter {
                it.id != user.id
            }.toMutableSet()
        }
        blogPostRepo.save(blogPost)
        userRepo.save(user)
    }

    override fun commentOnPost(comment: CommentDto) {
        // Get entities
        val user = userService.getCurrentlySignedInUser()
        val blogPost = blogPostRepo.findById(comment.blogPostId).orElseThrow{ TechbiesBlogPostNotFoundException() }
        val commentEntity = commentRepo.save(UserComment(user, comment.content, blogPost))
        // Alter entities
        blogPost.comments = blogPost.comments.plus(commentEntity)
        user.comments = user.comments.plus(commentEntity)
        // Save them
        blogPostRepo.save(blogPost)
        userRepo.save(user)
    }

    override fun getBookMarksOfUser(): Set<UnpublishedBlogPostDto> {
        TODO("Not yet implemented")
    }

    override fun getUserFeed(): Set<PublishedBlogPostDto> {
        TODO("Not yet implemented")
    }

    override fun deleteCommentOnPost(commentId: Long) {
        userOwnsCommentOrThrow(commentId)
        commentRepo.deleteById(commentId)
    }

    override fun userOwnsCommentOrThrow(commentId: Long) {
        val comment = commentRepo.findById(commentId).orElseThrow { TechbiesCommentNotFoundException() }
        val user = userService.getCurrentlySignedInUser()
        if (user != comment.author) {
            throw TechbiesIllegalCommentAccessException()
        }
    }

}