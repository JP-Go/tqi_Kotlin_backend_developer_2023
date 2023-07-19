package com.jumarket.selfcheckout.exceptions

class CartItemNotFoundException(
    override val message: String = "Cart item not found",
    override val resourceName: String = "cart item",
) :
    MissingResourceException(message, resourceName)
