package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.views.ProductCategoryView
import com.jumarket.selfcheckout.views.productCategoryViewFromEntity
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
    fun findAllCategories(): List<ProductCategory> {
        return productCategoryService.findAllProductCategory()
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findCategory(@PathVariable id: Long): ProductCategoryView {
        val category = productCategoryService.findById(id)
        return productCategoryViewFromEntity(category)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@RequestBody @Valid dto: ProductCategoryDTO): ProductCategoryView {
        val productCategorySaved = productCategoryService.createNewCategory(dto)
        return productCategoryViewFromEntity(productCategorySaved)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Nothing> {
        productCategoryService.deleteById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun changeCategoryName(
            @PathVariable id: Long,
            @RequestBody @Valid dto: ProductCategoryDTO
    ): ResponseEntity<ProductCategoryView> {
        val updatedCategory = productCategoryService.changeProductCategoryName(id, dto.name)
        return ResponseEntity(
                productCategoryViewFromEntity(updatedCategory),
                HttpStatus.OK,
        )
    }
}
