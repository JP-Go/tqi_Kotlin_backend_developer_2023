package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.ProductDTO
import com.jumarket.selfcheckout.entities.Product

interface IProductService {

    fun findById(id: Long): Product
    fun findAll(): List<Product>
    fun createProduct(dto: ProductDTO): Product
}
