package ru.melowetty.bankservice.model

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class CreateClientRequest(
    @NotBlank(message = "Название клиента не должно быть пустым")
    @Length(min = 3, max = 256, message = "Длина названия клиента не должно быть короче 3 символов и длиннее 256")
    val name: String,

    @NotBlank(message = "Короткое название клиента не должно быть пустым")
    @Length(min = 2, max = 64, message = "Длина короткого названия клиента не должно быть короче 2 символов и длиннее 64")
    val shortName: String,

    @NotBlank(message = "Адрес клиента не должен быть пустым")
    @Length(min = 3, max = 256, message = "Длина адреса клиента не должно быть короче 3 символов и длинее 256")
    val address: String,

    val organizationalLegalForm: OrganizationalLegalForm,
)
