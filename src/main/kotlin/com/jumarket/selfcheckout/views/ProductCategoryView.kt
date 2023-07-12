package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.ProductCategory

data class ProductCategoryView(val name: String, val id: Long?)

fun productCategoryViewFromEntity(entity: ProductCategory) =
        ProductCategoryView(entity.name, entity.id)
