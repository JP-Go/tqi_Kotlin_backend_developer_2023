package com.jumarket.selfcheckout.views

import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.entities.ProductCategory
import java.math.BigDecimal

data class ProductView(
        val id: Long?,
        val name: String,
        val unit: String,
        val price: BigDecimal,
        val category: ProductCategoryView?
)

fun productViewfromEntity(entity: Product) =
        ProductView(
                entity.id,
                entity.productName,
                entity.unit,
                entity.price,
                if (entity.productCategory == null) null
                else productCategoryViewFromEntity(entity.productCategory as ProductCategory)
        )
