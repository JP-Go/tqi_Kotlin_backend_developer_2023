package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.Cart

data class CartView(val id: Long, val items: List<CartItemView>)

fun cartFromEntity(entity: Cart) =
        CartView(id = entity.id as Long, items = entity.items.map { cartItemViewFromEntity(it) })
