package ru.melowetty.bankservice.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
data class Deposit(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val client: Client,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val bank: Bank,

    @Column(nullable = false)
    val dateOfOpen: LocalDateTime,

    @Column(nullable = false)
    val percent: Int,

    @Column(nullable = false)
    val duration: Int,

    @Column(nullable = false, columnDefinition="BOOLEAN DEFAULT false")
    val isOutDated: Boolean = false,
)