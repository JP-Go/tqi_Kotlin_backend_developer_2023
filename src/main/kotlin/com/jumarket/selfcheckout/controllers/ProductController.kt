package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.ProductDTO
import com.jumarket.selfcheckout.dtos.SetProductCategoryDTO
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.services.IProductService
import com.jumarket.selfcheckout.views.ProductView
import com.jumarket.selfcheckout.views.productViewfromEntity
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
        val productService: IProductService,
        val productCategoryService: IProductCategoryService,
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get products", description = "Gets all products registered")
    fun getAllProducts(): List<ProductView> {
        val products = productService.findAll().map { productViewfromEntity(it) }
        return products
    }

    @GetMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get product", description = "Gets a product by its id")
    fun getProduct(@PathVariable productId: Long): ProductView {
        val product = productService.findById(productId)
        return productViewfromEntity(product)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create product",
            description =
                    "Creates a new product if given its name, unit of measurement and price, and returns it. Optionally, a product category id can be given to set the product category uppon creation"
    )
    fun createProduct(@RequestBody @Valid dto: ProductDTO): ProductView {
        val savedProduct = productService.createProduct(dto)
        return productViewfromEntity(savedProduct)
    }

    @PatchMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Sets product",
            description =
                    "Sets the product category if given its id and the productCategoryId. If null is explictly passed to productCategoryId, the category association is removed"
    )
    fun setProductCategory(
            @PathVariable productId: Long,
            @RequestBody @Valid setProductCategoryDTO: SetProductCategoryDTO,
    ): ProductView {
        val savedProduct =
                productService.setProductCategory(
                        productId,
                        setProductCategoryDTO.productCategoryId,
                )
        return productViewfromEntity(savedProduct)
    }
}
