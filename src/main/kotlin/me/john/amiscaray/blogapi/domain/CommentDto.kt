package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class CommentDto(
    @ApiModelProperty(value = "id of the comment", required = false)
    val id: Long,
    @ApiModelProperty(value = "id of the blog post", required = true)
    val blogPostId: Long,
    @ApiModelProperty(value = "content of the comment", required = true)
    val content: String
    )