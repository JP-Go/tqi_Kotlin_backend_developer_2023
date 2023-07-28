package com.jumarket.selfcheckout.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.Retention
import kotlin.annotation.Target
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [SupportedPaymentMethodValidator::class])
annotation class SupportedPaymentMethod(
        val message: String =
                "Unsupported payment method. Supported payment methods are: DEBIT_CARD, CREDIT_CARD, PIX, CASH",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class SupportedPaymentMethodValidator : ConstraintValidator<SupportedPaymentMethod, String> {

    override fun isValid(str: String?, ctx: ConstraintValidatorContext): Boolean {
        if (str === null) return false
        if (str !in listOf("DEBIT_CARD", "CREDIT_CARD", "PIX", "CASH")) return false
        return true
    }
}
