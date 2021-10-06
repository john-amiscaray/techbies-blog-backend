package me.john.amiscaray.blogapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootApplication
class BlogApiApplication{

    @Bean("notImplemented")
    fun notImplementedResponse(): ResponseEntity<Any>{

        return ResponseEntity
            .status(HttpStatus.NOT_IMPLEMENTED)
            .build()

    }

}

fun main(args: Array<String>) {
    runApplication<BlogApiApplication>(*args)

}
