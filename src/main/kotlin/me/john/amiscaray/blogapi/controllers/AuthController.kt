package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(description = "Controller for the Authentication requests")
@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {

    @ApiOperation(value = "Sign up for the application")
    @PostMapping("/signup")
    fun signUp(@RequestBody authRequest: AuthRequest): ResponseEntity<Any>{

        userService.signUpUser(authRequest)

        return ResponseEntity.noContent().build()

    }

    @ApiOperation(value = "Login to the application", notes = "Returns a token for authentication purposes." +
            " The Token must be put in the Authorization header for every non-authentication request like so: " +
            "Bearer TOKEN")
    @PostMapping("/login")
    fun logIn(@RequestBody authRequest: AuthRequest): ResponseEntity<String>{

        return ResponseEntity.ok(userService.loginUser(authRequest))

    }

}