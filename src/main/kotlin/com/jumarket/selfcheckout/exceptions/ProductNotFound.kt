package com.jumarket.selfcheckout.exceptions

class ProductNotFoundException(
    override val message: String = "Product not found",
    override val resourceName: String = "product",
) :
    MissingResourceException(message, resourceName)
