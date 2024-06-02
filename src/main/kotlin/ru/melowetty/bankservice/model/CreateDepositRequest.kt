package ru.melowetty.bankservice.model

data class CreateDepositRequest(
    val clientId: Long,
    val bankId: Long,
    val percent: Int,
    val duration: Int,
)
