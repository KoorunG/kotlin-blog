package org.koorung.kotlinblog.exception

import org.springframework.validation.FieldError

data class ExceptionResponse(
    val count: Int,
    val code: Int,
    val message: String,
    val validation: Map<String, String?> = mapOf(),
)

fun (List<FieldError>).exceptionResults(code: Int, message: String): ExceptionResponse =
    ExceptionResponse(
        count = size,
        code = code,
        message = message,
        validation = mapOf<String, String>().plus(map { it.field to it.defaultMessage })
    )
