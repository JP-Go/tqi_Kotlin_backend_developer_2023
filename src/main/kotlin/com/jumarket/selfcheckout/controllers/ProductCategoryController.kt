package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.views.ProductCategoryView
import com.jumarket.selfcheckout.views.productCategoryViewFromEntity
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-category")
class ProductCategoryController(val productCategoryService: IProductCategoryService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Finds product categories", description = "Finds all product categories")
    fun findAllCategories(): List<ProductCategory> {
        return productCategoryService.findAllProductCategory()
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Find product category by id",
            description = "Finds a product category by its product category id"
    )
    fun findCategory(@PathVariable productId: Long): ProductCategoryView {
        val category = productCategoryService.findById(productId)
        return productCategoryViewFromEntity(category)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create product category",
            description = "Creates a new product category if given the name and returns it"
    )
    fun createCategory(@RequestBody @Valid dto: ProductCategoryDTO): ProductCategoryView {
        val productCategorySaved = productCategoryService.createNewCategory(dto)
        return productCategoryViewFromEntity(productCategorySaved)
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete product category",
            description = "Deletes a product category if given the id. Returns nothing"
    )
    fun deleteCategory(@PathVariable productId: Long): ResponseEntity<Nothing> {
        productCategoryService.deleteById(productId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Change product category name",
            description = "Changes the product category name if given the new name and returns it"
    )
    fun changeCategoryName(
            @PathVariable productId: Long,
            @RequestBody @Valid dto: ProductCategoryDTO
    ): ResponseEntity<ProductCategoryView> {
        val updatedCategory = productCategoryService.changeProductCategoryName(productId, dto.name)
        return ResponseEntity(
                productCategoryViewFromEntity(updatedCategory),
                HttpStatus.OK,
        )
    }
}
