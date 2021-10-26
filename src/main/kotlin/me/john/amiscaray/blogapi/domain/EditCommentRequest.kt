package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class EditCommentRequest(
    @ApiModelProperty(value = "The new content of the comment")
    val newContent: String
    )