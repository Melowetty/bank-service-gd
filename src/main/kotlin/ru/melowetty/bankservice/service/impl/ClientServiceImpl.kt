package ru.melowetty.bankservice.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest
import ru.melowetty.bankservice.repository.ClientRepository
import ru.melowetty.bankservice.service.ClientService

@Service
class ClientServiceImpl(
    private val clientRepository: ClientRepository
): ClientService {
    override fun getClientById(id: Long): Client? {
        TODO("Not yet implemented")
    }

    override fun deleteClientById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun patchClient(id: Long, fields: Map<String, Any?>): Client? {
        TODO("Not yet implemented")
    }

    override fun editClient(editClientRequest: EditClientRequest): Client? {
        TODO("Not yet implemented")
    }

    override fun createClient(createClientRequest: CreateClientRequest): Client {
        TODO("Not yet implemented")
    }

    override fun getAllClients(): List<Client> {
        TODO("Not yet implemented")
    }

    override fun getSortedClientsByField(field: String): List<Client> {
        TODO("Not yet implemented")
    }
}