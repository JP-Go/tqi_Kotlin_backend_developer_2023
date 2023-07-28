package com.jumarket.selfcheckout.exceptions

class CartAlreadyPaidException(override val message: String) : IllegalOperationException(message)
