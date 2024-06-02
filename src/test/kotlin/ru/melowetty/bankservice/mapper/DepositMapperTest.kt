package ru.melowetty.bankservice.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.melowetty.bankservice.dto.BankShortDto
import ru.melowetty.bankservice.dto.ClientShortDto
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.entity.Deposit
import ru.melowetty.bankservice.model.OrganizationalLegalForm
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DepositMapperTest {

    @Mock
    private lateinit var bankMapper: BankMapper

    @Mock
    private lateinit var clientMapper: ClientMapper

    @InjectMocks
    private lateinit var depositMapper: DepositMapper

    @Test
    fun `test toDto`() {
        val bank = Bank(id = 1, name = "Test Bank", bic = "123456", deposits = listOf())
        val client = Client(
            id = 1,
            name = "Test Client",
            shortName = "TC",
            address = "Test Address",
            organizationalLegalForm = OrganizationalLegalForm.AO,
            deposits = listOf()
        )
        val deposit = Deposit(
            id = 1,
            client = client,
            bank = bank,
            dateOfOpen = LocalDateTime.now(),
            percent = 5,
            duration = 12,
            isOutDated = false
        )
        val bankShortDto = BankShortDto(id = 1, name = "Test Bank", bic = "123456")
        val clientShortDto = ClientShortDto(
            id = 1,
            name = "Test Client",
            shortName = "TC",
            address = "Test Address",
            organizationalLegalForm = OrganizationalLegalForm.AO
        )

        Mockito.`when`(bankMapper.toShortDto(bank)).thenReturn(bankShortDto)
        Mockito.`when`(clientMapper.toShortDto(client)).thenReturn(clientShortDto)

        val result = depositMapper.toDto(deposit)

        assertEquals(deposit.id, result.id)
        assertEquals(deposit.dateOfOpen, result.dateOfOpen)
        assertEquals(deposit.percent, result.percent)
        assertEquals(deposit.duration, result.duration)
        assertEquals(deposit.isOutDated, result.isOutDated)
        assertEquals(bankShortDto, result.bank)
        assertEquals(clientShortDto, result.client)
    }

    @Test
    fun `test toShortDto`() {
        val bank = Bank(id = 1, name = "Test Bank", bic = "123456", deposits = listOf())
        val client = Client(
            id = 1,
            name = "Test Client",
            shortName = "TC",
            address = "Test Address",
            organizationalLegalForm = OrganizationalLegalForm.AO,
            deposits = listOf()
        )
        val deposit = Deposit(
            id = 1,
            client = client,
            bank = bank,
            dateOfOpen = LocalDateTime.now(),
            percent = 5,
            duration = 12,
            isOutDated = false
        )

        val result = depositMapper.toShortDto(deposit)

        assertEquals(deposit.id, result.id)
        assertEquals(deposit.dateOfOpen, result.dateOfOpen)
        assertEquals(deposit.percent, result.percent)
        assertEquals(deposit.duration, result.duration)
        assertEquals(bank.id, result.bankId)
        assertEquals(client.id, result.clientId)
    }
}
