package ru.melowetty.bankservice.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.melowetty.bankservice.dto.DepositInfo
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.entity.Deposit
import ru.melowetty.bankservice.model.OrganizationalLegalForm
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ClientMapperTest {

    @Mock
    private lateinit var depositInfoMapper: DepositInfoMapper

    @InjectMocks
    private lateinit var clientMapper: ClientMapper

    @Test
    fun `test toDto`() {
        val deposit = Deposit(1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12, isOutDated = false)
        val client = Client(id = 1, name = "Test Client", shortName = "TC", address = "Test Address", organizationalLegalForm = OrganizationalLegalForm.AO, deposits = listOf(deposit))
        val depositInfo = DepositInfo(id = 1, duration = 12, dateOfOpen = LocalDateTime.now(), percent = 5, isOutDated = false)

        Mockito.`when`(depositInfoMapper.toDepositInfo(deposit)).thenReturn(depositInfo)

        val result = clientMapper.toDto(client)

        assertEquals(client.id, result.id)
        assertEquals(client.name, result.name)
        assertEquals(client.shortName, result.shortName)
        assertEquals(client.address, result.address)
        assertEquals(client.organizationalLegalForm, result.organizationalLegalForm)
        assertEquals(1, result.deposits.size)
    }

    @Test
    fun `test toShortDto`() {
        val client = Client(id = 1, name = "Test Client", shortName = "TC", address = "Test Address", organizationalLegalForm = OrganizationalLegalForm.AO, deposits = listOf())

        val result = clientMapper.toShortDto(client)

        assertEquals(client.id, result.id)
        assertEquals(client.name, result.name)
        assertEquals(client.shortName, result.shortName)
        assertEquals(client.address, result.address)
        assertEquals(client.organizationalLegalForm, result.organizationalLegalForm)
    }
}
