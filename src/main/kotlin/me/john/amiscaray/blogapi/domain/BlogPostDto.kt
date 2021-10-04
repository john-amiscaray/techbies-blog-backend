package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModelProperty

data class BlogPostDto(
    @ApiModelProperty(value = "the id for the blog post", required = false)
    private val id: Long,
    @ApiModelProperty(value = "title of the blog post", required = true)
    private val title: String,
    @ApiModelProperty(value = "title of the blog post", required = true)
    private val content: String,
    @ApiModelProperty(value = "id of the author of the blog post", required = true)
    private val authorId: Long
)