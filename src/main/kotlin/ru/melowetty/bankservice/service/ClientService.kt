package ru.melowetty.bankservice.service

import jakarta.validation.Valid
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest

interface ClientService {
    fun getClientById(id: Long): Client?
    fun deleteClientById(id: Long)
    fun patchClient(id: Long, fields: Map<String, Any?>): Client?
    fun editClient(id: Long, @Valid editClientRequest: EditClientRequest): Client?
    fun createClient(@Valid createClientRequest: CreateClientRequest): Client
    fun getAllClients(): List<Client>
    fun getSortedClientsByField(field: String): List<Client>
}