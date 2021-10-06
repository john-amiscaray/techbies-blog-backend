package me.john.amiscaray.blogapi.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.john.amiscaray.blogapi.domain.AuthRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(description = "Controller for the Authentication requests")
@RestController
@RequestMapping("/auth")
class AuthController(@Qualifier("notImplemented") private val notImplementedResponse: ResponseEntity<Any>) {

    @ApiOperation(value = "Sign up for the application | NOT IMPLEMENTED", notes = "Must verify email afterwards")
    @PostMapping("/signup")
    fun signUp(@RequestBody authRequest: AuthRequest): ResponseEntity<Any>{

        return notImplementedResponse

    }

    @ApiOperation(value = "Login to the application | NOT IMPLEMENTED", notes = "Returns a token and userID in this format: " +
            "'<TOKEN> <ID>'. The TOKEN must be put in the authorization header like so 'Bearer <TOKEN>'. The ID should be " +
            "stored for future API operations")
    @PostMapping("/login")
    fun logIn(@RequestBody authRequest: AuthRequest): ResponseEntity<Any>{

        return notImplementedResponse

    }

}