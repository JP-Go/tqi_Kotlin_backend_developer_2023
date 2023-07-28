package com.jumarket.selfcheckout.exceptions

class CartNotFoundException(
    override val message: String = "Cart not found",
    override val resourceName: String = "cart",
) :
    MissingResourceException(message, resourceName)
