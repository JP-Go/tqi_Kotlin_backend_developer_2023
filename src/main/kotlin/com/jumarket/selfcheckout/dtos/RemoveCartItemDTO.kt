package com.jumarket.selfcheckout.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RemoveCartItemDTO(
        @field:NotNull(message = "Missing productId")
        @field:Positive(message = "productId must be positive number")
        var productId: Long
)
