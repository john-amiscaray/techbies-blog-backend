package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.CommentDto
import me.john.amiscaray.blogapi.entities.BlogPost
import org.springframework.stereotype.Service
import javax.xml.stream.events.Comment

@Service
class BlogPostServiceImp : BlogPostService {
    override fun getBlogPostsOfUser(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun getBookMarksOfUser(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }

    override fun likePost(userId: Long, blogPostId: Long) {
        TODO("Not yet implemented")
    }

    override fun bookMarkPost(userId: Long, blogPostId: Long) {
        TODO("Not yet implemented")
    }

    override fun commentOnPost(userId: Long, blogPostId: Long, comment: CommentDto) {
        TODO("Not yet implemented")
    }

    override fun getCommentsOnPost(blogPostId: Long): Set<Comment> {
        TODO("Not yet implemented")
    }

    override fun getUserFeed(userId: Long): Set<BlogPost> {
        TODO("Not yet implemented")
    }
}