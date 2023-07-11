package com.jumarket.selfcheckout.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "product_category")
data class ProductCategory(
        @Column(nullable = false) var name: String = "",
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)
