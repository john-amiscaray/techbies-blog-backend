package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class PublishedBlogPostDto(
    id: Long=-1,
    title: String?,
    content: String?,
    tags: String?,
    @ApiModelProperty(value = "Comments for the blog post")
    val comments: Set<CommentDto>,
    @ApiModelProperty(value = "The number of wow reactions for the blog post")
    val wowReactions: Int,
    @ApiModelProperty(value = "The number of like reactions for the blog post")
    val likeReactions: Int,
    @ApiModelProperty(value = "The number of sad reactions for the blog post")
    val sadReactions: Int,
    @ApiModelProperty(value = "The number of angry reactions for the blog post")
    val angryReactions: Int
): UnpublishedBlogPostDto(id=id, title=title, content=content, tags=tags)