package ru.melowetty.bankservice.dto

data class BankShortDto(
    val id: Long,
    val name: String,
    val bic: String,
)
