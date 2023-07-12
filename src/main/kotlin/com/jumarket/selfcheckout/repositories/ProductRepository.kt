package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface ProductRepository : JpaRepository<Product, Long>
