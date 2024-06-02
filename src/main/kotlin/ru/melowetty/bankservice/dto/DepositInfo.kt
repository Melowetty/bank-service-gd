package ru.melowetty.bankservice.dto

import java.time.LocalDateTime

data class DepositInfo(
    val id: Long,

    val dateOfOpen: LocalDateTime,

    val percent: Int,

    val duration: Int,
)
