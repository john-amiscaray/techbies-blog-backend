package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.john.amiscaray.blogapi.entities.User

@ApiModel
data class AuthRequest(
    @ApiModelProperty(value = "username", required = true)
    val email: String,
    @ApiModelProperty(value = "password", required = true)
    val password: String){

    fun toNewUser(): User{

        return User(-1, email, password)

    }

}