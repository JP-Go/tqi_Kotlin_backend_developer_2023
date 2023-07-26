package com.jumarket.selfcheckout.services

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import com.jumarket.selfcheckout.exceptions.CartAlreadyPaidException
import com.jumarket.selfcheckout.exceptions.CartNotFoundException
import com.jumarket.selfcheckout.exceptions.ProductAlreadyOnCartException
import com.jumarket.selfcheckout.repositories.CartItemRepository
import com.jumarket.selfcheckout.repositories.CartRepository
import com.jumarket.selfcheckout.services.impl.CartService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import java.math.BigDecimal
import java.util.Optional
import java.util.Random
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CartServiceTest {
    @MockK lateinit var cartRepository: CartRepository
    @MockK lateinit var cartItemRepository: CartItemRepository
    @InjectMockKs lateinit var ut: CartService

    @Test
    fun `should be able to find a cart given the id`() {
        val fakeId = Random().nextLong()
        val fakeCart = buildCart(id = fakeId)

        every { cartRepository.findById(fakeId) } returns Optional.of(fakeCart)

        val actual = ut.findCart(fakeId)

        Assertions.assertThat(actual).isNotNull.isSameAs(fakeCart)
        Assertions.assertThat(actual.id).isNotNull.isEqualTo(fakeId)

        verify(exactly = 1) { cartRepository.findById(fakeId) }
    }

    @Test
    fun `should not be able to find a cart if given an invalid id and throw a CartNotFoundException`() {
        val fakeId = Random().nextLong()

        every({ cartRepository.findById(any()) }) returns Optional.empty()

        Assertions.assertThatExceptionOfType(CartNotFoundException::class.java)
                .isThrownBy { ut.findCart(fakeId) }
                .withMessage("Cart with id $fakeId not found")
    }

    @Test
    fun `should be able to add a cart item on a cart`() {
        val fakeId = Random().nextLong()
        val fakeCart = buildCart(id = fakeId)
        val fakeCartItem = buildCartItem(cart = fakeCart)

        every { cartItemRepository.save(fakeCartItem) } returns fakeCartItem
        every { cartItemRepository.findOne<CartItem>(any()) } returns Optional.empty()

        Assertions.assertThatNoException().isThrownBy({ ut.addCartItem(fakeCartItem) })
    }

    @Test
    fun `should not be able to add itens to a paid cart and throw CartAlreadyPaidException`() {
        val fakeId = Random().nextLong()
        val fakeCart = buildCart(id = fakeId, paid = true)
        val fakeCartItem = buildCartItem(cart = fakeCart)

        every { cartItemRepository.findOne<CartItem>(any()) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(CartAlreadyPaidException::class.java)
                .isThrownBy { ut.addCartItem(fakeCartItem) }
                .withMessage("Can not add itens to a paid cart")
    }

    @Test
    fun `should not be able to add the same item to a cart`() {
        val fakeId = Random().nextLong()
        val fakeCart = buildCart(id = fakeId, paid = false)
        val fakeCartItem = buildCartItem(cart = fakeCart)

        every { cartItemRepository.findOne<CartItem>(any()) } returns Optional.of(fakeCartItem)

        Assertions.assertThatExceptionOfType(ProductAlreadyOnCartException::class.java)
                .isThrownBy { ut.addCartItem(fakeCartItem) }
                .withMessage("Product ${fakeCartItem.product?.productName} already on cart")
    }

    @Test
    fun `should be able to pay a cart`() {
        val fakeId = Random().nextLong()
        val fakeCart = buildCart(id = fakeId, paid = false)

        every { cartRepository.save(fakeCart) } returns fakeCart

        ut.payCart(fakeCart, PaymentMethod.DEBIT_CARD)

        Assertions.assertThat(fakeCart.paid).isNotNull.isNotEqualTo(false)
        Assertions.assertThat(fakeCart.paymentMethod).isNotNull.isEqualTo(PaymentMethod.DEBIT_CARD)
    }

    private fun buildCart(
            id: Long = 1L,
            paid: Boolean = false,
            paymentMethod: PaymentMethod? = null,
            items: List<CartItem> = emptyList()
    ) = Cart(id = id, paid = paid, paymentMethod = paymentMethod, items = items)

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
