package com.jumarket.selfcheckout.exceptions

import java.time.LocalDateTime

data class ExceptionDetails(
        val title: String,
        val message: String,
        val timestamp: LocalDateTime,
        val details: MutableMap<String, String?>
)
