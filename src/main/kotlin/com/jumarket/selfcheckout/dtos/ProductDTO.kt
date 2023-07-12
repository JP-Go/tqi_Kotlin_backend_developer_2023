package com.jumarket.selfcheckout.dtos

import java.math.BigDecimal

data class ProductDTO(
        val name: String,
        val unit: String,
        val price: BigDecimal,
        var productCategory: Long?
)
