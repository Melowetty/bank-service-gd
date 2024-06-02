package ru.melowetty.bankservice.dto

import java.time.LocalDateTime

data class DepositDto(
    val id: Long,

    val client: ClientShortDto,

    val bank: BankShortDto,

    val dateOfOpen: LocalDateTime,

    val percent: Int,

    val duration: Int,

    val isOutDated: Boolean,
)
