package com.jumarket.selfcheckout.exceptions

class CartItemNotFoundException(override val message: String = "Cart item not found") :
    RuntimeException(message)
