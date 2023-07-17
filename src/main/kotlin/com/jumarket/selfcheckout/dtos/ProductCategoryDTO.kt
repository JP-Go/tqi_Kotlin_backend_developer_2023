package com.jumarket.selfcheckout.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductCategoryDTO(
        @field:NotNull(message = "Missing name")
        @field:NotBlank(message = "Name must have length of at least 1 character")
        val name: String
)
