package com.jumarket.selfcheckout.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Table(name = "product")
@Entity
data class Product(
        @Column(nullable = false, name = "product_name") var productName: String = "",
        @Column(nullable = false) var unit: String = "",
        @Column(nullable = false) var price: BigDecimal = BigDecimal.ZERO,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
        @ManyToOne
        @JoinColumn(name = "product_category_id")
        var productCategory: ProductCategory? = null
)
