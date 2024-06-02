package ru.melowetty.bankservice.dto

import ru.melowetty.bankservice.model.OrganizationalLegalForm

data class ClientShortDto(
    val id: Long,
    val name: String,
    val shortName: String,
    val address: String,
    val organizationalLegalForm: OrganizationalLegalForm,
)
