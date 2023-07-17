package com.jumarket.selfcheckout.dtos

import jakarta.validation.constraints.NotNull

data class RemoveCartItemDTO(@field:NotNull(message = "Missing productId") var productId: Long)
