package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE cart = ?1 AND product = ?2")
    fun findByCartAndProduct(cart: Cart, product: Product): Optional<CartItem>
}
