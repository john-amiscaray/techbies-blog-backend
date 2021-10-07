package me.john.amiscaray.blogapi.data

import me.john.amiscaray.blogapi.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{

    fun findUserByUsername(name: String): User?

}