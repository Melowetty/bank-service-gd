package ru.melowetty.bankservice.controller

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.melowetty.bankservice.dto.ClientDto
import ru.melowetty.bankservice.dto.ClientShortDto
import ru.melowetty.bankservice.mapper.ClientMapper
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest
import ru.melowetty.bankservice.service.ClientService

@RestController
@RequestMapping("client")
class ClientController(
    private val clientService: ClientService,
    private val clientMapper: ClientMapper,
) {
    @GetMapping
    fun getAllClients(@RequestParam sortBy: String = ""): List<ClientShortDto> {
        return if(sortBy.isNotBlank()) {
            clientService.getSortedClientsByField(field = sortBy).map { clientMapper.toShortDto(it) }
        } else {
            clientService.getAllClients().map { clientMapper.toShortDto(it) }
        }
    }

    @PostMapping
    fun createClient(@RequestBody createClientRequest: CreateClientRequest): ClientDto {
        return clientMapper.toDto(clientService.createClient(createClientRequest))
    }

    @PutMapping("/{id}")
    fun fullEditClient(@PathVariable id: Long, @RequestBody editClientRequest: EditClientRequest): ClientDto {
        return clientService.editClient(id, editClientRequest)?.let { clientMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }

    @PatchMapping("/{id}")
    fun partEditclient(@PathVariable id: Long, fields: Map<String, Any?>): ClientDto {
        return clientService.patchClient(id, fields)?.let { clientMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteClient(@PathVariable id: Long) {
        return clientService.deleteClientById(id)
    }

    @GetMapping("/{id}")
    fun getClient(@PathVariable id: Long): ClientDto {
        return clientService.getClientById(id)?.let { clientMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }
}