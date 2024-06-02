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
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class BankMapperTest {

    @Mock
    private lateinit var depositInfoMapper: DepositInfoMapper

    @InjectMocks
    private lateinit var bankMapper: BankMapper

    @Test
    fun `test toDto`() {
        val deposit = Deposit(1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12, isOutDated = false)
        val bank = Bank(id = 1, name = "Test Bank", bic = "123456", deposits = listOf(deposit))
        val depositInfo = DepositInfo(id = 1, duration = 12, dateOfOpen = LocalDateTime.now(), percent = 5, isOutDated = false)

        Mockito.`when`(depositInfoMapper.toDepositInfo(deposit)).thenReturn(depositInfo)

        val result = bankMapper.toDto(bank)

        assertEquals(bank.id, result.id)
        assertEquals(bank.name, result.name)
        assertEquals(bank.bic, result.bic)
        assertEquals(1, result.deposits.size)
    }

    @Test
    fun `test toShortDto`() {
        val bank = Bank(id = 1, name = "Test Bank", bic = "123456", deposits = listOf())

        val result = bankMapper.toShortDto(bank)

        assertEquals(bank.id, result.id)
        assertEquals(bank.name, result.name)
        assertEquals(bank.bic, result.bic)
    }
}
