package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface CartItemRepository : JpaRepository<CartItem, Long>
