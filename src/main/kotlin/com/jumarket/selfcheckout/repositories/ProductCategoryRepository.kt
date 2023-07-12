package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface ProductCategoryRepository : JpaRepository<ProductCategory, Long>

