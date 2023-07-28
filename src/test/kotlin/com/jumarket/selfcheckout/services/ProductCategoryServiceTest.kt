package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.ProductCategoryDTO
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.exceptions.ProductCategoryNotFoundException
import com.jumarket.selfcheckout.repositories.ProductCategoryRepository
import com.jumarket.selfcheckout.services.impl.ProductCategoryService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import java.util.Optional
import java.util.Random
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductCategoryServiceTest {

    @MockK lateinit var productCategoryRepository: ProductCategoryRepository

    @InjectMockKs lateinit var productCategoryService: ProductCategoryService

    @Test
    fun `should be able to save a product category`() {
        val fakeCategory = buildProductCategory()
        val dto = buildProductCategoryDTO(fakeCategory.name)
        every { productCategoryRepository.save(any()) } returns fakeCategory

        val category = productCategoryService.createNewCategory(dto)

        Assertions.assertThat(category).isNotNull
        Assertions.assertThat(category).isSameAs(fakeCategory)
    }

    @Test
    fun `should be able to find a product category by its id`() {
        val fakeId = Random().nextLong()
        val fakeCategory = buildProductCategory(id = fakeId)
        every { productCategoryRepository.findById(fakeId) } returns Optional.of(fakeCategory)

        val category = productCategoryService.findById(fakeId)

        Assertions.assertThat(category).isNotNull
        Assertions.assertThat(category).isExactlyInstanceOf(ProductCategory::class.java)
        Assertions.assertThat(category).isSameAs(fakeCategory)
        verify(exactly = 1) { productCategoryRepository.findById(fakeId) }
    }

    @Test
    fun `should not be able to find a product category with an invalid id and throw a ProductCategoryNotFound exception`() {
        val fakeId = Random().nextLong()
        every { productCategoryRepository.findById(fakeId) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(ProductCategoryNotFoundException::class.java)
                .isThrownBy { productCategoryService.findById(fakeId) }
                .withMessage("Product category with id $fakeId not found")

        verify(exactly = 1) { productCategoryRepository.findById(fakeId) }
    }

    @Test
    fun `should return a list of product categories`() {
        val fakeCategories =
                listOf(1, 2, 3).map {
                    buildProductCategory(name = "Cat $it", id = Random().nextLong())
                }
        every { productCategoryRepository.findAll() } returns fakeCategories

        val categories = productCategoryService.findAllProductCategory()

        Assertions.assertThat(categories).isNotNull
        Assertions.assertThat(categories.size).isEqualTo(3)
        verify(exactly = 1) { productCategoryRepository.findAll() }
    }

    @Test
    fun `should be able to delete an entity`() {
        val fakeId = Random().nextLong()
        val fakeCategory = buildProductCategory(id = fakeId)
        every { productCategoryRepository.findById(fakeId) } returns Optional.of(fakeCategory)
        every { productCategoryRepository.delete(fakeCategory) } just runs

        productCategoryService.deleteById(fakeId)

        verify(exactly = 1) { productCategoryRepository.findById(fakeId) }
        verify(exactly = 1) { productCategoryRepository.delete(fakeCategory) }
    }

    private fun buildProductCategory(name: String = "Frutas", id: Long = 1L) =
            ProductCategory(name = name, id = id)
    private fun buildProductCategoryDTO(name: String = "Frutas") = ProductCategoryDTO(name = name)
}
