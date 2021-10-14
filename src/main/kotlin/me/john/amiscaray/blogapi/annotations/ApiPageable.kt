package me.john.amiscaray.blogapi.annotations

import io.swagger.annotations.ApiImplicitParam

import io.swagger.annotations.ApiImplicitParams


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@ApiImplicitParams(
    ApiImplicitParam(
        name = "page",
        dataType = "int",
        paramType = "query",
        value = "Results page you want to retrieve (0..N)"
    ),
    ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
)
internal annotation class ApiPageable