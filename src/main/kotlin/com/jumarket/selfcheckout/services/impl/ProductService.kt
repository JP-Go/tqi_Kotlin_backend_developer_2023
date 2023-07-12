package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.dtos.ProductDTO
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.repositories.ProductRepository
import com.jumarket.selfcheckout.services.IProductCategoryService
import com.jumarket.selfcheckout.services.IProductService
import org.springframework.stereotype.Service

@Service
class ProductService(
    val repository: ProductRepository,
    val categoryService: IProductCategoryService,
) : IProductService {

    override fun findById(id: Long): Product {
        return repository.findById(id).orElseThrow {
            throw IllegalArgumentException("Product with id $id was not found")
        }
    }

    override fun findAll(): List<Product> {
        return repository.findAll()
    }

    override fun createProduct(dto: ProductDTO): Product {
    override fun setProductCategory(id: Long, productCategoryId: Long?): Product {
        val product = this.findById(id).apply {
            productCategory = productCategoryId?.let { categoryService.findById(it) }
        }
        return repository.save(product)
    }

        dto.productCategory
                ?.let {
                    val category = categoryService.findById(it)
                    return repository.save(
                            Product(
                                    productName = dto.name,
                                    price = dto.price,
                                    unit = dto.unit,
                                    productCategory = category
                            )
                    )
                }
                .run {
                    return repository.save(
                            Product(
                                    productName = dto.name,
                                    price = dto.price,
                                    unit = dto.unit,
                            )
                    )
                }
    }
}
