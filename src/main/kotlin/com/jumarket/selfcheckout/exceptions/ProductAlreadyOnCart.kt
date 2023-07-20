package com.jumarket.selfcheckout.exceptions

open class ProductAlreadyOnCartException(override val message: String) : IllegalOperationException(message)
