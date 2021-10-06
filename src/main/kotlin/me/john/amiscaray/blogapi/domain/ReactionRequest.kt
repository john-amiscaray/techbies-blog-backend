package me.john.amiscaray.blogapi.domain

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
data class ReactionRequest(
    @ApiModelProperty(value = "reaction", required = true)
    private val reaction: ReactionType,
    @ApiModelProperty(value = "Whether or not this was a request to remove a reaction. " +
            "If false, adds a reaction.", required = true)
    private val isRemoveReaction: Boolean
) {
    enum class ReactionType{

        Wow,
        Like,
        Worried,
        Sad,
        Angry

    }
}