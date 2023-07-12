package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.ProductDTO
import com.jumarket.selfcheckout.dtos.SetProductCategoryDTO
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.services.IProductService
import com.jumarket.selfcheckout.views.ProductView
import com.jumarket.selfcheckout.views.productViewfromEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    val productService: IProductService,
    val productCategoryService: IProductCategoryService,
) {

    @GetMapping
    fun getAllProducts(): List<ProductView> {
        val products = productService.findAll().map { productViewfromEntity(it) }
        return products
    }

    @GetMapping("{productId}")
    fun getProduct(@PathVariable productId: Long): ProductView {
        val product = productService.findById(productId)
        return productViewfromEntity(product)
    }

    @PostMapping
    fun createProduct(@RequestBody dto: ProductDTO): Product {
        val savedProduct = productService.createProduct(dto)
        return savedProduct
    }

    @PatchMapping("{productId}")
    fun setToProductCategory(
        @PathVariable productId: Long,
        @RequestBody setProductCategoryDTO: SetProductCategoryDTO,
    ): ProductView {
        val savedProduct =
            productService.setProductCategory(
                productId,
                setProductCategoryDTO.productCategoryId,
            )
        return productViewfromEntity(savedProduct)
    }
}
