package ru.melowetty.bankservice.dto

data class BankDto(
    val id: Long,
    val name: String,
    val bic: String,
    val deposits: List<DepositInfo>
)
