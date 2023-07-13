package com.jumarket.selfcheckout.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.OneToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import java.math.BigDecimal

@Entity
@Table(name = "cart_item")
data class CartItem(
    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null,
    @OneToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,
    var quantity: Int = 0,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(name = "total_price")
    var totalPrice: BigDecimal = BigDecimal.ZERO
)