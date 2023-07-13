package com.jumarket.selfcheckout.entities

import com.jumarket.selfcheckout.enumerations.PaymentMethod
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "cart")
data class Cart(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var paid: Boolean = false,
        @Column(name = "payment_method") @Enumerated var paymentMethod: PaymentMethod? = null,
        @OneToMany(mappedBy = "cart") var items: List<CartItem> = listOf()
)
