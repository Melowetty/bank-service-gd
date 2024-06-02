package ru.melowetty.bankservice.model

data class EditDepositRequest(
    val clientId: Long,
    val bankId: Long,
    val percent: Int,
    val duration: Int,
)
