package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import java.math.BigDecimal

data class CartView(
        val id: Long,
        val items: List<CartItemView>,
        val paid: Boolean,
        val total: BigDecimal,
        val method: PaymentMethod?
)

fun cartFromEntity(entity: Cart) =
        CartView(
                id = entity.id as Long,
                items = entity.items.map { cartItemViewFromEntity(it) },
                paid = entity.paid,
                method = entity.paymentMethod,
                total =
                        entity.items.fold(BigDecimal.ZERO) { a: BigDecimal, b: CartItem ->
                            a + b.totalPrice
                        }
        )
