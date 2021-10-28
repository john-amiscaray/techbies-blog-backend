package me.john.amiscaray.blogapi.entities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Tag(
    @Id
    val name: String
)