package com.jumarket.selfcheckout.exceptions

class ProductNotFoundException(override val message: String = "Product not found") :
        RuntimeException(message)
