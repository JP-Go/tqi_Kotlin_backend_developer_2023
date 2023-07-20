package com.jumarket.selfcheckout.dtos

import com.jumarket.selfcheckout.validators.SupportedPaymentMethod
import jakarta.validation.constraints.NotNull

data class PayCartDTO(
        @field:NotNull(message = "Missing paymentMethod")
        @field:SupportedPaymentMethod
        var paymentMethod: String,
)
