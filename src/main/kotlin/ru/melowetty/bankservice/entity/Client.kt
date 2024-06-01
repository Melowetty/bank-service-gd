package ru.melowetty.bankservice.entity

import jakarta.persistence.*
import ru.melowetty.bankservice.model.OrganizationalLegalForm

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(nullable = false, length = 256)
    val name: String,

    @Column(nullable = false, length = 64)
    val shortName: String,

    @Column(nullable = false, length = 256)
    val address: String,

    @Column(nullable = false, length = 64)
    @Enumerated(value = EnumType.STRING)
    val organizationalLegalForm: OrganizationalLegalForm,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    val deposits: List<Deposit>,
)
