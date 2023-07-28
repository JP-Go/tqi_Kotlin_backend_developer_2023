package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.enumerations.PaymentMethod

interface ICartService {
    fun createCart(): Cart
    fun findCart(id: Long): Cart
    fun addCartItem(cartItem: CartItem)
    fun removeCartItem(cartItem: CartItem)
    fun payCart(cart: Cart, method: PaymentMethod)
    fun changeItemQuantity(cartItem: CartItem, newQuantity: Int): Cart
}
