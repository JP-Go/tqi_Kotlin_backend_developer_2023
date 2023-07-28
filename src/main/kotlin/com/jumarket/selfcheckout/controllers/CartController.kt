package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.CartItemDTO
import com.jumarket.selfcheckout.dtos.PayCartDTO
import com.jumarket.selfcheckout.dtos.RemoveCartItemDTO
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import com.jumarket.selfcheckout.services.ICartItemService
import com.jumarket.selfcheckout.services.ICartService
import com.jumarket.selfcheckout.services.IProductService
import com.jumarket.selfcheckout.views.CartView
import com.jumarket.selfcheckout.views.cartFromEntity
import jakarta.validation.Valid
import java.math.BigDecimal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cart")
class CartController(
        private val cartItemService: ICartItemService,
        private val cartService: ICartService,
        private val productService: IProductService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewCart(): CartView {
        val cart = cartService.createCart()
        return cartFromEntity(cart)
    }

    @GetMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    fun getCart(@PathVariable cartId: Long): CartView {
        val cart = cartService.findCart(cartId)
        return cartFromEntity(cart)
    }

    @PostMapping("/{cartId}/add")
    @ResponseStatus(HttpStatus.OK)
    fun addProductToCart(
            @PathVariable cartId: Long,
            @RequestBody @Valid dto: CartItemDTO
    ): CartView {
        val cart = cartService.findCart(cartId)
        val cartItem = cartItemService.createCartItem(cart, dto)
        cartService.addCartItem(cartItem)
        return cartFromEntity(cart)
    }

    @PatchMapping("/{cartId}/remove")
    @ResponseStatus(HttpStatus.OK)
    fun removeProductFromCart(
            @PathVariable cartId: Long,
            @RequestBody @Valid dto: RemoveCartItemDTO
    ): ResponseEntity<CartView> {
        val cart = cartService.findCart(cartId)
        val cartItem = cartItemService.findCartItemOnCart(cart, dto.productId)
        cartService.removeCartItem(cartItem)
        return ResponseEntity(cartFromEntity(cart), HttpStatus.OK)
    }

    @PatchMapping("/{cartId}/checkout")
    @ResponseStatus(HttpStatus.OK)
    fun payCart(@PathVariable cartId: Long, @RequestBody @Valid dto: PayCartDTO): String {
        val cart = cartService.findCart(cartId)
        val method = PaymentMethod.valueOf(dto.paymentMethod)
        val total: BigDecimal = cart.items.fold(BigDecimal.ZERO) { acc, it -> it.totalPrice + acc }
        cartService.payCart(cart, method)
        return "Cart with id ${cart.id}, total: R\$ $total was paid by $method"
    }
}
