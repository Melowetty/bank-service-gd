package ru.melowetty.bankservice.dto

import ru.melowetty.bankservice.model.OrganizationalLegalForm

data class ClientDto(
    val id: Long,
    val name: String,
    val shortName: String,
    val address: String,
    val organizationalLegalForm: OrganizationalLegalForm,
    val deposits: List<DepositInfo>
)
