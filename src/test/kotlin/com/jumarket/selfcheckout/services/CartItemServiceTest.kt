package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.dtos.CartItemDTO
import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.exceptions.CartItemNotFoundException
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.services.impl.CartItemService
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
class CartItemServiceTest {

        @MockK lateinit var cartItemRepository: CartItemRepository

        @MockK lateinit var productService: IProductService

        @InjectMockKs lateinit var ut: CartItemService

        @Test
        fun `should be able to create an cart item`() {
                val fakeProduct = Product("Name", "un", BigDecimal(10.0), 1L, null)
                val fakeCart = Cart(1L, false, null, emptyList())
                val quantity = 2
                val cartItemDto = CartItemDTO(productId = fakeProduct.id!!, quantity = quantity)
                val expected =
                                buildCartItem(
                                                product = fakeProduct,
                                                cart = fakeCart,
                                                quantity = quantity
                                )
                val expectedPrice = fakeProduct.price * BigDecimal(quantity)

                every { productService.findById(fakeProduct.id!!) } returns fakeProduct

                val actual = ut.createCartItem(fakeCart, cartItemDto)

                Assertions.assertThat(actual).isNotNull
                Assertions.assertThat(actual.cart).isNotNull.isEqualTo(expected.cart)
                Assertions.assertThat(actual.product).isNotNull.isEqualTo(expected.product)
                Assertions.assertThat(actual.totalPrice).isEqualTo(expectedPrice)
        }

        @Test
        fun `should be able to find a cart item by product and cart`() {
                val fakeProduct = Product("Name", "un", BigDecimal(10.0), 1L, null)
                val fakeCart = Cart(1L, false, null, emptyList())
                val expected = buildCartItem(product = fakeProduct, cart = fakeCart, quantity = 2)

                every { productService.findById(fakeProduct.id!!) } returns fakeProduct
                every { cartItemRepository.findByCartAndProduct(fakeCart, fakeProduct) } returns
                                Optional.of(expected)

                val actual = ut.findCartItemOnCart(fakeCart, fakeProduct.id!!)

                Assertions.assertThat(actual).isNotNull
                Assertions.assertThat(actual.totalPrice).isEqualTo(expected.totalPrice)
                Assertions.assertThat(actual.cart).isEqualTo(expected.cart)
                Assertions.assertThat(actual.product).isEqualTo(expected.product)
        }

        @Test
        fun `should not be able to find cart item if provided invalid cart and product and throw CartItemNotFoundException`() {
                val fakeProduct = Product("Name", "un", BigDecimal(10.0), 1L, null)
                val fakeCart = Cart(1L, false, null, emptyList())

                every { productService.findById(any()) } returns fakeProduct
                every { cartItemRepository.findByCartAndProduct(any(), any()) } returns
                                Optional.empty()

                Assertions.assertThatExceptionOfType(CartItemNotFoundException::class.java)
                                .isThrownBy { ut.findCartItemOnCart(fakeCart, fakeProduct.id!!) }
                                .withMessage(
                                                "Product with id ${fakeProduct.id} not in cart ${fakeCart.id}"
                                )
        }

        @Test
        fun `should not be able to find cart item if provided an invalid id and throw CartItemNotFoundException`() {
                val fakeLong = Random().nextLong()

                every { cartItemRepository.findById(any()) } returns Optional.empty()

                Assertions.assertThatExceptionOfType(CartItemNotFoundException::class.java)
                                .isThrownBy { ut.findCartItem(fakeLong) }
                                .withMessage("Cart item with id $fakeLong not found")
        }

        private fun buildCartItem(
                        id: Long = 1L,
                        cart: Cart = Cart(),
                        product: Product = Product(price = BigDecimal(10)),
                        quantity: Int = 1
        ) =
                        CartItem(
                                        id = id,
                                        cart = cart,
                                        product = product,
                                        quantity = quantity,
                                        totalPrice = product.price.times(BigDecimal(quantity))
                        )
}
