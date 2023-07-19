package com.jumarket.selfcheckout.exceptions

class ProductCategoryNotFoundException(
        override val message: String = "Product category not found"
) : RuntimeException(message)
