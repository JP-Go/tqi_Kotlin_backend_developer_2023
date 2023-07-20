package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.dtos.CartItemDTO
import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.exceptions.CartItemNotFoundException
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.services.ICartItemService
import com.jumarket.selfcheckout.services.IProductService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CartItemService(
    private val cartItemRepository: CartItemRepository,
    private val productService: IProductService,
) : ICartItemService {

    override fun findCartItem(id: Long): CartItem {
        return cartItemRepository.findById(id).orElseThrow {
            throw CartItemNotFoundException("Cart item with id $id not found")
        }
    }

    override fun findCartItemOnCart(cart: Cart, productId: Long): CartItem {
        val product = productService.findById(productId)
        return cartItemRepository.findByCartAndProduct(cart, product).orElseThrow {
            throw CartItemNotFoundException("Product with id $productId not in cart ${cart.id}")
        }
    }

    override fun createCartItem(cart: Cart, dto: CartItemDTO): CartItem {
        val quantity = dto.quantity
        val product = productService.findById(dto.productId)
        val totalPrice: BigDecimal = product.price * BigDecimal(quantity)
        return CartItem(
            cart = cart,
            product = product,
            quantity = quantity,
            totalPrice = totalPrice,
        )
    }
}
