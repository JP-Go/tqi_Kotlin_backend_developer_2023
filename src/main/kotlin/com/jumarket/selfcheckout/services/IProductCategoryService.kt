package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory

interface IProductCategoryService {

    fun findById(id: Long): ProductCategory
    fun deleteById(id: Long): Unit
    fun changeProductCategoryName(id: Long, name: String): ProductCategory
    fun findAllProductCategory(): List<ProductCategory>
    fun createNewCategory(dto: ProductCategoryDTO): ProductCategory
}
