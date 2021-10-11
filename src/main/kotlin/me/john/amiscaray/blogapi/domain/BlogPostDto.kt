package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class BlogPostDto(
    @ApiModelProperty(value = "the id for the blog post", required = false)
    val id: Long,
    @ApiModelProperty(value = "title of the blog post", required = true)
    val title: String,
    @ApiModelProperty(value = "title of the blog post", required = true)
    val content: String,
    @ApiModelProperty(value = "a comma separated list of tags to give the post", required = true)
    val tags: String
)