package org.koorung.kotlinblog.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.koorung.kotlinblog.exception.exceptionResults
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    val logger = KotlinLogging.logger { }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception::class)
    fun fieldErrorHandler(e: MethodArgumentNotValidException) =
        if(e.hasFieldErrors()) e.fieldErrors.exceptionResults(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다!") else null
}