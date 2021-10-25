package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class BookmarkRequest(
    @ApiModelProperty(value = "Id of the blog post to bookmark", required = true)
    val blogPostId: Long,
    @ApiModelProperty(value = "Whether or not this was a request to remove a bookmark. If false, adds a bookmark.",
        required = true)
    val isRemoveBookmark: Boolean
)