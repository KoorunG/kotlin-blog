package org.koorung.kotlinblog.controller

import org.koorung.kotlinblog.exception.exceptionResults
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun fieldErrorHandler(e: MethodArgumentNotValidException) =
        if(e.hasFieldErrors()) e.fieldErrors.exceptionResults(BAD_REQUEST.value(), "잘못된 요청입니다!") else null
}

