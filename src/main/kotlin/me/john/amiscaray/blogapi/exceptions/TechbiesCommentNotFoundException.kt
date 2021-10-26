package me.john.amiscaray.blogapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class TechbiesCommentNotFoundException: NoSuchElementException("The comment you were looking for cannot be found")