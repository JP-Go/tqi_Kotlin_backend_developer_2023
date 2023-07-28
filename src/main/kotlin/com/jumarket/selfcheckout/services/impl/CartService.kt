package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import com.jumarket.selfcheckout.exceptions.CartAlreadyPaidException
import com.jumarket.selfcheckout.exceptions.CartNotFoundException
import com.jumarket.selfcheckout.exceptions.ProductAlreadyOnCartException
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.repositories.CartRepository
import com.jumarket.selfcheckout.services.ICartService
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service

@Service
class CartService(
        private val cartRepository: CartRepository,
        private val cartItemRepository: CartItemRepository,
) : ICartService {

    override fun createCart(): Cart {
        return cartRepository.save(Cart())
    }

    override fun findCart(id: Long): Cart {
        return cartRepository.findById(id).orElseThrow {
            throw CartNotFoundException("Cart with id $id not found")
        }
    }

    override fun addCartItem(cartItem: CartItem) {
        if (cartItem.cart!!.paid) throw CartAlreadyPaidException("Can not add itens to a paid cart")
        if (isProductOnCart(cartItem))
                throw ProductAlreadyOnCartException(
                        "Product ${cartItem.product?.productName} already on cart"
                )
        cartItemRepository.save(cartItem)
    }

    override fun removeCartItem(cartItem: CartItem) {
        if (cartItem.cart!!.paid)
                throw CartAlreadyPaidException("Can not remove itens of a paid cart")
        cartItemRepository.delete(cartItem)
    }

    override fun payCart(cart: Cart, method: PaymentMethod) {
        if (cart.paid) throw CartAlreadyPaidException("Can not pay a cart twice")
        cart.apply {
            paid = true
            paymentMethod = method
        }
        cartRepository.save(cart)
    }

    fun isProductOnCart(cartItem: CartItem): Boolean {
        val matcher = ExampleMatcher.matching().withIgnorePaths("quantity", "totalPrice")
        val cartItemExample =
                Example.of(CartItem(cart = cartItem.cart, product = cartItem.product), matcher)
        return cartItemRepository.findOne(cartItemExample).isPresent()
    }
}
