package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.CartItemDTO
import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem

interface ICartItemService {
    fun findCartItem(id: Long): CartItem
    fun findCartItemOnCart(cart: Cart, productId: Long): CartItem
    fun createCartItem(cart: Cart, dto: CartItemDTO): CartItem
}
