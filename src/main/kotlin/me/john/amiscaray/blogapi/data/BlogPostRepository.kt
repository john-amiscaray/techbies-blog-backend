package me.john.amiscaray.blogapi.data

import me.john.amiscaray.blogapi.entities.BlogPost
import me.john.amiscaray.blogapi.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

// TODO : add find by day/time functionality (will be paginated for recent section)
@Repository
interface BlogPostRepository : PagingAndSortingRepository<BlogPost, Long>{

    fun findAllByAuthorAndTagsContaining(author: User, tags: String): Set<BlogPost>

    fun existsByIdAndAuthor(id: Long, author: User): Boolean

    fun findAllByAuthor(author: User): Set<BlogPost>

}