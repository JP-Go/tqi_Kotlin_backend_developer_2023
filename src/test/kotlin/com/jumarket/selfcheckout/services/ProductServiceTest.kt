package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.ProductDTO
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.exceptions.ProductNotFoundException
import com.jumarket.selfcheckout.repositories.ProductRepository
import com.jumarket.selfcheckout.services.impl.ProductService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.math.BigDecimal
import java.util.Optional
import java.util.Random
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProductServiceTest {
    @MockK lateinit var categoryService: IProductCategoryService

    @MockK lateinit var productRepository: ProductRepository

    @InjectMockKs lateinit var productService: ProductService

    @Test
    fun `should be able to save a product with or without category`() {
        val fakeProduct1 = buildProduct()
        val fakeProduct2 =
                buildProduct(
                        productCategory = ProductCategory(id = Random().nextLong(), name = "Frutas")
                )

        val fakeProductDTO1 = buildProductDTO(fakeProduct1)
        val fakeProductDTO2 = buildProductDTO(fakeProduct2)

        every { productRepository.save(any()) } returns fakeProduct1 andThen fakeProduct2
        every { categoryService.findById(fakeProductDTO2.productCategory!!) } returns
                fakeProduct2.productCategory!!

        val actual1 = productService.createProduct(fakeProductDTO1)

        Assertions.assertThat(actual1).isNotNull
        Assertions.assertThat(actual1).isEqualTo(fakeProduct1)
        Assertions.assertThat(actual1.productCategory).isNull()

        val actual2 = productService.createProduct(fakeProductDTO2)

        Assertions.assertThat(actual2).isNotNull
        Assertions.assertThat(actual2).isEqualTo(fakeProduct2)
        Assertions.assertThat(actual2.productCategory).isNotNull
        Assertions.assertThat(actual2.productCategory).isEqualTo(fakeProduct2.productCategory)
    }

    @Test
    fun `should be able to find a product by its id`() {
        val fakeId = Random().nextLong()
        val fakeProduct =
                buildProduct(
                        productCategory =
                                ProductCategory(id = Random().nextLong(), name = "Frutas"),
                        id = fakeId,
                )
        every { productRepository.findById(fakeId) } returns Optional.of(fakeProduct)
        every { categoryService.findById(fakeProduct.productCategory?.id!!) } returns
                fakeProduct.productCategory!!

        val actual = productService.findById(fakeId)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isEqualTo(fakeProduct)
    }

    @Test
    fun `should not be able to find product with invalid id and throw an ProductNotFoundException`() {
        val fakeId = Random().nextLong()
        every { productRepository.findById(fakeId) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(ProductNotFoundException::class.java)
                .isThrownBy { productService.findById(fakeId) }
                .withMessage("Product with id $fakeId was not found")
    }

    @Test
    fun `should be able to set the category of a product`() {
        val fakeId = Random().nextLong()
        val fakeCategoryId = Random().nextLong()
        val fakeProduct = buildProduct(id = fakeId)
        val category = ProductCategory(id = fakeCategoryId, name = "Test")
        val expected = fakeProduct.copy(productCategory = category)

        every { productRepository.findById(fakeId) } returns Optional.of(fakeProduct)
        every { categoryService.findById(fakeCategoryId) } returns category
        every { productRepository.save(any()) } returns fakeProduct

        val actual = productService.setProductCategory(fakeId, fakeCategoryId)

        Assertions.assertThat(actual).isNotNull.isEqualTo(expected)
        Assertions.assertThat(actual.productCategory).isNotNull.isEqualTo(expected.productCategory)

        val actual2 = productService.setProductCategory(fakeId, null)

        val expected2 = expected.copy(productCategory = null)
        Assertions.assertThat(actual2).isNotNull.isEqualTo(expected2)
        Assertions.assertThat(actual2.productCategory).isNull()
    }

    private fun buildProduct(
            productName: String = "Maçã",
            unit: String = "un",
            price: BigDecimal = BigDecimal.ZERO,
            id: Long? = Random().nextLong(),
            productCategory: ProductCategory? = null,
    ) =
            Product(
                    productName,
                    unit,
                    price,
                    id,
                    productCategory,
            )

    private fun buildProductDTO(entity: Product) =
            ProductDTO(entity.productName, entity.unit, entity.price, entity.productCategory?.id)
}
