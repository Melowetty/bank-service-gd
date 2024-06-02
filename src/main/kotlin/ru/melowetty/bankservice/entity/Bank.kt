package ru.melowetty.bankservice.entity

import jakarta.persistence.*

@Entity
data class Bank(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(nullable = false, length = 256)
    val name: String,

    @Column(nullable = false)
    val bic: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    val deposits: List<Deposit>,
)
