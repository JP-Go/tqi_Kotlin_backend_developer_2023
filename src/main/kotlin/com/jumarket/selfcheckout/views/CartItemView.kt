package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.CartItem
import java.math.BigDecimal

data class CartItemView(
                var productName: String,
                var unit: String,
                var quantity: Int,
                var unitPrice: BigDecimal,
                var totalPrice: BigDecimal,
)

fun cartItemViewFromEntity(entity: CartItem) =
                CartItemView(
                                productName = entity.product!!.productName,
                                unit = entity.product!!.unit,
                                quantity = entity.quantity,
                                unitPrice = entity.product!!.price,
                                totalPrice = entity.totalPrice
                )
