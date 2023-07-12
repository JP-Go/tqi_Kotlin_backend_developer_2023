package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.ProductCategory

sealed class ProductCategoryView(val name: String, val id: Long?) {
    class FromEntity(entity: ProductCategory) : ProductCategoryView(entity.name, entity.id)
}
