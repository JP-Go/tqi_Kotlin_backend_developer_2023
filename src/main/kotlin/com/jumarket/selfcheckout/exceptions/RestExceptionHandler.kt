package com.jumarket.selfcheckout.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

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
                title = "Bad request",
                message = "Missing or invalid fields",
                timestamp = LocalDateTime.now(),
                details = errors,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(MissingResourceException::class)
    fun handleMissingResourceException(
        ex: MissingResourceException,
    ): ResponseEntity<ExceptionDetails> {
        return ResponseEntity(
            ExceptionDetails(
                title = "Bad request",
                message = "Missing resource",
                timestamp = LocalDateTime.now(),
                details =
                hashMapOf(
                    ex.resourceName to ex.message,
                ),
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(IllegalOperationException::class)
    fun handleIllegalAccessException(
        ex: IllegalOperationException,
    ): ResponseEntity<ExceptionDetails> {
        return ResponseEntity(
            ExceptionDetails(
                title = "Bad request",
                message = "Illegal operation",
                timestamp = LocalDateTime.now(),
                details = hashMapOf(
                    "details" to ex.message,
                ),

            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}
