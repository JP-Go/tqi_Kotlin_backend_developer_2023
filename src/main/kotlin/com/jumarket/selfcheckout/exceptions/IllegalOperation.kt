package com.jumarket.selfcheckout.exceptions

open class IllegalOperationException(override val message: String = "Can not access this resource") : RuntimeException(message)
