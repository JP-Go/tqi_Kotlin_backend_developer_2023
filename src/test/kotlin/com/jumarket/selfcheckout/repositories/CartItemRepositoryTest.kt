package com.jumarket.selfcheckout.repositories

import com.jumarket.selfcheckout.entities.Cart
import com.jumarket.selfcheckout.entities.CartItem
import com.jumarket.selfcheckout.entities.Product
import com.jumarket.selfcheckout.entities.ProductCategory
import com.jumarket.selfcheckout.enumerations.PaymentMethod
import java.math.BigDecimal
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartItemRepositoryTest {

    @Autowired lateinit var cartItemRepository: CartItemRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var product1: Product
    private lateinit var product2: Product
    private lateinit var cart: Cart
    private lateinit var cartItem: CartItem
    @BeforeEach
    fun setup() {
        product1 = testEntityManager.persist(buildProduct(productName = "Maçã"))
        product2 = testEntityManager.persist(buildProduct(productName = "Laranja"))
        cart = testEntityManager.persist(buildCart())
        cartItem =
                testEntityManager.persist(
                        buildCartItem(product = product1, cart = cart, totalPrice = product1.price)
                )
    }

    @Test
    fun `should be able to find a cart item by product and cart`() {
        val actual = cartItemRepository.findByCartAndProduct(cart, product1).get()

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual.cart).isNotNull.isSameAs(cart)
        Assertions.assertThat(actual.product).isNotNull.isSameAs(product1)
    }

    private fun buildCartItem(
            product: Product,
            cart: Cart,
            quantity: Int = 1,
            totalPrice: BigDecimal = BigDecimal.ZERO
    ) = CartItem(product = product, cart = cart, quantity = quantity, totalPrice = totalPrice)

    private fun buildProduct(
            productName: String = "Maçã",
            price: BigDecimal = BigDecimal(10),
            unit: String = "un",
            productCategory: ProductCategory? = null
    ) =
            Product(
                    productName = productName,
                    price = price,
                    unit = unit,
                    productCategory = productCategory
            )

    private fun buildCart(paid: Boolean = false, paymentMethod: PaymentMethod? = null) =
            Cart(paid = paid, paymentMethod = paymentMethod)
}
