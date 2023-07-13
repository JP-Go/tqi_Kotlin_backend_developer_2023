package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product

interface ICartItemService {
    fun findCartItem(id: Long): CartItem
    fun createCartItem(cart: Cart, product: Product, quantity: Int): CartItem
}
