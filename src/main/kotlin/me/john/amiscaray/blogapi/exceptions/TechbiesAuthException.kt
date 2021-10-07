package me.john.amiscaray.blogapi.exceptions

import org.springframework.security.core.AuthenticationException

class TechbiesAuthException(error: String): AuthenticationException(error)