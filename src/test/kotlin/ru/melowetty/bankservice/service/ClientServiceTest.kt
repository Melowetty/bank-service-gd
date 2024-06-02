package ru.melowetty.bankservice.service

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.model.CreateClientRequest
import ru.melowetty.bankservice.model.EditClientRequest
import ru.melowetty.bankservice.model.OrganizationalLegalForm
import ru.melowetty.bankservice.repository.ClientRepository
import ru.melowetty.bankservice.service.impl.ClientServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class ClientServiceTest {
    @InjectMocks
    private lateinit var clientService: ClientServiceImpl
    @Mock
    private lateinit var clientRepository: ClientRepository

    @Test
    fun `normal get all clients`() {
        val expected = listOf(
            Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO),
            Client(id = 2, name = "Tinkoff2", shortName = "TB2", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        )

        Mockito.`when`(clientRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        val actual = clientService.getAllClients()

        Assertions.assertEquals(expected, actual, "Списки со всеми клиентами не равны изначальным банкам")
    }

    @Test
    fun `get sorted clients by short name`() {
        val expected = listOf(
            Client(id = 2, name = "Tinkoff2", shortName = "TB2", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO),
            Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        )
        Mockito.`when`(clientRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        val actual = clientService.getSortedClientsByField(field = "shortName")

        Assertions.assertEquals(expected.sortedBy { it.shortName }, actual, "Списки с отсортированными клиентами по полю shortName не равны")
    }

    @Test
    fun `get sorted clients by not exists field`() {
        val expected = listOf(
            Client(id = 2, name = "Tinkoff2", shortName = "TB2", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO),
            Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        )
        Mockito.`when`(clientRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        assertThrows<RuntimeException> {
            clientService.getSortedClientsByField(field = "nonexistsfield")
        }
    }

    @Test
    fun `get client by id when it is exists`() {
        val expected = Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)

        Mockito.`when`(clientRepository.findById(1)).thenReturn(Optional.of(expected))

        val actual = clientService.getClientById(1)

        Assertions.assertEquals(expected, actual, "Клиент который должен быть не найден")
    }

    @Test
    fun `get client by id when it is no exists`() {
        Mockito.`when`(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty())

        val actual = clientService.getClientById(1)

        Assertions.assertNull(actual, "Клиент который не существует найден")
    }

    @Test
    fun `delete client by id, which exists`() {
        Mockito.`when`(clientRepository.existsById(1)).thenReturn(true)
        clientService.deleteClientById(1)
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(1)
    }

    @Test
    fun `delete client by id, which not exists`() {
        Mockito.`when`(clientRepository.existsById(1)).thenReturn(false)
        assertThrows<EntityNotFoundException> {
            clientService.deleteClientById(1)
        }
        Mockito.verify(clientRepository, Mockito.never()).deleteById(1)
    }

    @Test
    fun `patch client`() {
        val before = Client(id = 1, name = "Tinkoff2", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        val after = Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)

        Mockito.`when`(clientRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(clientRepository.save(after)).thenReturn(after)
        val actual = clientService.patchClient(1, mapOf("name" to "Tinkoff"))
        Assertions.assertEquals(actual, after, "Клиент не изменился после запроса на частичное изменение")
    }

    @Test
    fun `create client`() {
        val expected = Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        val createClientRequest = CreateClientRequest(
            name = "Tinkoff",
            shortName = "TB",
            address = "Moscow",
            organizationalLegalForm = OrganizationalLegalForm.AO
        )
        Mockito.`when`(clientRepository.save(expected.copy(id = 0))).thenReturn(expected)
        val actual = clientService.createClient(createClientRequest)
        Assertions.assertEquals(expected, actual, "Клиент не добавился после запроса на добавление")
    }

    @Test
    fun `edit exists client`() {
        val request = EditClientRequest(
            id = 1,
            name = "Tinkoff",
            shortName = "TB",
            address = "Moscow",
            organizationalLegalForm = OrganizationalLegalForm.AO,
        )
        val before = Client(id = 1, name = "Tinkoff2", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        val after = Client(id = 1, name = "Tinkoff", shortName = "TB", address = "Moscow", organizationalLegalForm = OrganizationalLegalForm.AO)
        Mockito.`when`(clientRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(clientRepository.save(after)).thenReturn(after)
        val actual = clientService.editClient(request)
        Assertions.assertEquals(actual, after, "Клиент не изменился после запроса на изменение")
    }

    @Test
    fun `edit non exists client`() {
        val request = EditClientRequest(
            id = 1,
            name = "Tinkoff",
            shortName = "TB",
            address = "Moscow",
            organizationalLegalForm = OrganizationalLegalForm.AO,
        )
        Mockito.`when`(clientRepository.findById(1)).thenReturn(Optional.empty())
        val actual = clientService.editClient(request)
        Assertions.assertNull(actual, "Клиент нашелся в запросе на изменение, хотя его не существует")
    }
}