package com.jumarket.selfcheckout.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CartItemDTO(
        @field:NotNull(message = "Missing productId")
        @field:Positive(message = "productId must be a positive integer")
        val productId: Long,
        @field:NotNull(message = "Missing quantity")
        @field:Positive(message = "quantity must be positive")
        val quantity: Int
)
