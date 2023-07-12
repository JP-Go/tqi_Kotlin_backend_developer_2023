package com.jumarket.selfcheckout.controllers

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.views.ProductCategoryView
import com.jumarket.selfcheckout.views.productCategoryViewFromEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-category")
class ProductCategoryController(val productCategoryService: IProductCategoryService) {

    @GetMapping
    fun findAllCategories(): List<ProductCategory> {
        return productCategoryService.findAllProductCategory()
    }

    @GetMapping("/{id}")
    fun findCategory(@PathVariable id: Long): ProductCategoryView {
        val category = productCategoryService.findById(id)
        return productCategoryViewFromEntity(category)
    }

    @PostMapping
    fun createCategory(@RequestBody dto: ProductCategoryDTO): ProductCategoryView {
        val productCategorySaved = productCategoryService.createNewCategory(dto)
        return productCategoryViewFromEntity(productCategorySaved)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Nothing> {
        productCategoryService.deleteById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{id}")
    fun changeCategoryName(
            @PathVariable id: Long,
            @RequestBody dto: ProductCategoryDTO
    ): ResponseEntity<ProductCategoryView> {
        val updatedCategory = productCategoryService.changeProductCategoryName(id, dto.name)
        return ResponseEntity(
                productCategoryViewFromEntity(updatedCategory),
                HttpStatus.OK,
        )
    }
}
