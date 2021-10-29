package me.john.amiscaray.blogapi.data

import me.john.amiscaray.blogapi.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepo : JpaRepository<Tag, String>