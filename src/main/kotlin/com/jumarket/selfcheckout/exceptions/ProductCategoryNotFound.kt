package com.jumarket.selfcheckout.exceptions

class ProductCategoryNotFoundException(
    override val message: String = "Product category not found",
    override val resourceName: String = "product category",
) :
    MissingResourceException(message, resourceName)
