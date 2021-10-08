package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            UserDetailsImpl(userRepository.findUserByUsername(username)
                ?: throw UsernameNotFoundException("User of username $username was not found"))
        }catch(error: UsernameNotFoundException){
            throw error
        }
    }
}