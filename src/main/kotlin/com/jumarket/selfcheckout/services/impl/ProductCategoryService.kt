package com.jumarket.selfcheckout.services.impl

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.exceptions.ProductCategoryNotFoundException
import com.jumarket.selfcheckout.repositories.ProductCategoryRepository
import com.jumarket.selfcheckout.services.IProductCategoryService
import org.springframework.stereotype.Service

@Service
class ProductCategoryService(private val repository: ProductCategoryRepository) :
        IProductCategoryService {

    override fun findById(id: Long): ProductCategory {
        return repository.findById(id).orElseThrow {
            throw ProductCategoryNotFoundException("Product category with $id not found")
        }
    }

    override fun deleteById(id: Long) {
        val toDelete = this.findById(id)
        repository.delete(toDelete)
    }

    override fun changeProductCategoryName(id: Long, name: String): ProductCategory {
        val toUpdate = this.findById(id)
        toUpdate.name = name
        repository.save(toUpdate)
        return toUpdate
    }

    override fun findAllProductCategory(): List<ProductCategory> {
        return repository.findAll()
    }

    override fun createNewCategory(dto: ProductCategoryDTO): ProductCategory {
        return repository.save(ProductCategory(dto.name))
    }
}
