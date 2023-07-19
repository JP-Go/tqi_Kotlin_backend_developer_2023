package com.jumarket.selfcheckout.exceptions

class CartNotFoundException(override val message: String = "Cart not found") :
    RuntimeException(message)
