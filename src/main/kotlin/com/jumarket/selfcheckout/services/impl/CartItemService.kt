package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.services.ICartItemService
import java.math.BigDecimal
import org.springframework.stereotype.Service

@Service
class CartItemService(
        private val cartItemRepository: CartItemRepository,
) : ICartItemService {

    override fun findCartItem(id: Long): CartItem {
        return cartItemRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Cart item with id $id not found.")
        }
    }

    override fun createCartItem(cart: Cart, product: Product, quantity: Int): CartItem {
        val totalPrice: BigDecimal = product.price * BigDecimal(quantity)
        return CartItem(
                cart = cart,
                product = product,
                quantity = quantity,
                totalPrice = totalPrice
        )
    }
}
