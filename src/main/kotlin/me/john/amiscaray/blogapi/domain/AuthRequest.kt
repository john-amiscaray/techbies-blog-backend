package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModelProperty
import me.john.amiscaray.blogapi.entities.User

data class AuthRequest(
    @ApiModelProperty(value = "username", required = true)
    val username: String,
    @ApiModelProperty(value = "password", required = true)
    val password: String){

    fun toNewUser(): User{

        return User(-1, username, password)

    }

}