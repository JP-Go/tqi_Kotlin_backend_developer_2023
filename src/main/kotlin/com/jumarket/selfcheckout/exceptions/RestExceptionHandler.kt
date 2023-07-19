package com.jumarket.selfcheckout.exceptions

import java.time.LocalDateTime
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler() {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.stream().forEach { error: ObjectError ->
            val field: String = (error as FieldError).field
            val message: String? = error.defaultMessage
            errors[field] = message
        }

        return ResponseEntity(
                ExceptionDetails(
                        title = "Bad Request",
                        message = "Missing or invalid fields",
                        timestamp = LocalDateTime.now(),
                        details = errors
                ),
                HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(
            ex: ProductNotFoundException
    ): ResponseEntity<ExceptionDetails> {
        return ResponseEntity(
                ExceptionDetails(
                        title = "Product not found",
                        message = ex.message,
                        timestamp = LocalDateTime.now(),
                        details =
                                hashMapOf(
                                        "product" to ex.message,
                                )
                ),
                HttpStatus.BAD_REQUEST
        )
    }
}
