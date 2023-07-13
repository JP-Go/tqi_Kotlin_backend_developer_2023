package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.repositories.CartRepository
import com.jumarket.selfcheckout.services.ICartService
import org.springframework.stereotype.Service

@Service
class CartService(
        private val cartRepository: CartRepository,
        private val cartItemRepository: CartItemRepository
) : ICartService {

    override fun createCart(): Cart {
        return cartRepository.save(Cart())
    }

    override fun findCart(id: Long): Cart {
        return cartRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Cart with id $id not found")
        }
    }

    override fun addCartItem(cartItem: CartItem) {
        cartItemRepository.save(cartItem)
    }

    override fun removeCartItem(cartItem: CartItem) {
        cartItemRepository.delete(cartItem)
    }

    override fun payCart(cart: Cart, method: PaymentMethod) {
        cart.apply {
            paid = true
            paymentMethod = method
        }
        cartRepository.save(cart)
    }
}
