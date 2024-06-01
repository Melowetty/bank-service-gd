package ru.melowetty.bankservice.entity

import jakarta.persistence.*

@Entity
data class Bank(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(nullable = false)
    val bic: String,
)
