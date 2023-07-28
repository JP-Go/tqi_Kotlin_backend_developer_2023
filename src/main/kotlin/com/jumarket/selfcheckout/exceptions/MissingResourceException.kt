package com.jumarket.selfcheckout.exceptions

abstract class MissingResourceException
(override val message: String, open val resourceName: String) : RuntimeException(message)
