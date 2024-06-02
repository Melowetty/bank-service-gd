package ru.melowetty.bankservice.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest
import ru.melowetty.bankservice.repository.ClientRepository
import ru.melowetty.bankservice.service.ClientService
import ru.melowetty.bankservice.utils.ObjectUtils

@Validated
@Service
class ClientServiceImpl(
    private val clientRepository: ClientRepository
): ClientService {
    override fun getClientById(id: Long): Client? {
        return clientRepository.findById(id).orElseGet { null }
    }

    override fun deleteClientById(id: Long) {
        val isExists = clientRepository.existsById(id)
        if (!isExists) throw EntityNotFoundException("Клиент с таким ID не найден!")
        clientRepository.deleteById(id)
    }

    override fun patchClient(id: Long, fields: Map<String, Any?>): Client? {
        val client = getClientById(id) ?: return null
        ObjectUtils.changeFields(Client::class.java, fields = fields, target = client)
        return clientRepository.save(client)
    }

    override fun editClient(@Valid editClientRequest: EditClientRequest): Client? {
        val client = getClientById(editClientRequest.id) ?: return null
        val newClient = client.copy(
            name = editClientRequest.name,
            shortName = editClientRequest.shortName,
            address = editClientRequest.address,
            organizationalLegalForm = editClientRequest.organizationalLegalForm
        )
        return clientRepository.save(newClient)
    }

    override fun createClient(@Valid createClientRequest: CreateClientRequest): Client {
        val client = Client(
            id = 0L,
            name = createClientRequest.name,
            shortName = createClientRequest.shortName,
            address = createClientRequest.address,
            organizationalLegalForm = createClientRequest.organizationalLegalForm
        )
        return clientRepository.save(client)
    }

    override fun getAllClients(): List<Client> {
        return clientRepository.findAll().toList()
    }

    override fun getSortedClientsByField(field: String): List<Client> {
        val clients = getAllClients()
        return ObjectUtils.sortByField(Client::class, field, clients)
            ?: throw RuntimeException("Поле с таким названием не найдено!")
    }
}