package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModelProperty

data class CommentDto(
    @ApiModelProperty(value = "id of the comment", required = false)
    private val id: Long,
    @ApiModelProperty(value = "id of the user commenting the post", required = true)
    private val userId: Long,
    @ApiModelProperty(value = "id of the blog post", required = true)
    private val blogPostId: Long,
    @ApiModelProperty(value = "content of the comment", required = true)
    private val content: String
    )