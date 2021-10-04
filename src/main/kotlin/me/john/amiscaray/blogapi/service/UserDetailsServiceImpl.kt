package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            UserDetailsImpl(userService.findUserByUsername(username))
        }catch(error: UsernameNotFoundException){
            throw error
        }
    }
}