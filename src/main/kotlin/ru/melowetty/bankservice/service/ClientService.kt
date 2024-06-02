package ru.melowetty.bankservice.service

import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest

interface ClientService {
    fun getClientById(id: Long): Client?
    fun deleteClientById(id: Long)
    fun patchClient(id: Long, fields: Map<String, Any?>): Client?
    fun editClient(editClientRequest: EditClientRequest): Client?
    fun createClient(createClientRequest: CreateClientRequest): Client
    fun getAllClient(): List<Client>
    fun getSortedClientByField(field: String): List<Client>
}