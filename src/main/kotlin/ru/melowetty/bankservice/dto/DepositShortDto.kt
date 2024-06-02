package ru.melowetty.bankservice.dto

import java.time.LocalDateTime

data class DepositShortDto(
    val id: Long,

    val clientId: Long,

    val bankId: Long,

    val dateOfOpen: LocalDateTime,

    val percent: Int,

    val duration: Int,
)
