package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.CartItemDTO
import com.jumarket.selfcheckout.dtos.PayCartDTO
import com.jumarket.selfcheckout.dtos.RemoveCartItemDTO
import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import com.jumarket.selfcheckout.services.ICartItemService
import com.jumarket.selfcheckout.services.ICartService
import com.jumarket.selfcheckout.services.IProductService
import com.jumarket.selfcheckout.views.CartView
import com.jumarket.selfcheckout.views.cartItemViewFromEntity
import java.math.BigDecimal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class CartController(
        private val cartItemService: ICartItemService,
        private val cartService: ICartService,
        private val productService: IProductService,
) {

    @PostMapping
    fun createNewCart(): Cart {
        return cartService.createCart()
    }

    @GetMapping("/{cartId}")
    fun getCart(@PathVariable cartId: Long): CartView {
        val cart = cartService.findCart(cartId)
        return CartView(id = cart.id as Long, items = cart.items.map { cartItemViewFromEntity(it) })
    }

    @PostMapping("/{cartId}/add")
    fun addProductToCart(@PathVariable cartId: Long, @RequestBody dto: CartItemDTO): CartView {
        val cart = cartService.findCart(cartId)
        val cartItem = cartItemService.createCartItem(cart, dto)
        cartService.addCartItem(cartItem)
        return CartView(id = cart.id as Long, items = cart.items.map { cartItemViewFromEntity(it) })
    }

    @PatchMapping("/{cartId}/remove")
    fun removeProductFromCart(
            @PathVariable cartId: Long,
            @RequestBody dto: RemoveCartItemDTO
    ): CartView {
        val cart = cartService.findCart(cartId)
        val cartItem = cartItemService.findCartItemOnCart(cart, dto.productId)
        cartService.removeCartItem(cartItem)
        return CartView(id = cart.id as Long, items = cart.items.map { cartItemViewFromEntity(it) })
    }

    @PatchMapping("/{cartId}/checkout")
    fun payCart(@PathVariable cartId: Long, @RequestBody dto: PayCartDTO): String {
        val cart = cartService.findCart(cartId)
        val method = PaymentMethod.valueOf(dto.paymentMethod)
        val total: BigDecimal = cart.items.fold(BigDecimal.ZERO) { acc, it -> it.totalPrice + acc }
        cartService.payCart(cart, method)
        return "Cart with id ${cart.id}, total: R\$ $total was paid by $method"
    }
}
