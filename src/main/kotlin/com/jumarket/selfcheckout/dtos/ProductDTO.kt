package com.jumarket.selfcheckout.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class ProductDTO(
        @field:NotNull(message = "Missing name")
        @field:NotBlank(message = "Name must have at least 1 character")
        val name: String,
        @field:NotNull(message = "Missing unit")
        @field:NotBlank(message = "Unit must have at least 1 character")
        val unit: String,
        @field:NotNull(message = "Missing price")
        @field:PositiveOrZero(message = "Price must be a positive number")
        val price: BigDecimal,
        var productCategory: Long?
)
