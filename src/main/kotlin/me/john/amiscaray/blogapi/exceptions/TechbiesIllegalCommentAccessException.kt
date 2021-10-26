package me.john.amiscaray.blogapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class TechbiesIllegalCommentAccessException: IllegalAccessException("You do not have permissions to alter or delete that comment")