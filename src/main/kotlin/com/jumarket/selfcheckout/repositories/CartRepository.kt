package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface CartRepository : JpaRepository<Cart, Long>
