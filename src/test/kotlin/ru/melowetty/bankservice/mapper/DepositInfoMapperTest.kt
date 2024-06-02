package ru.melowetty.bankservice.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.entity.Deposit
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DepositInfoMapperTest {

    private val depositInfoMapper = DepositInfoMapper()

    @Test
    fun `test toDepositInfo`() {
        val deposit = Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12, isOutDated = false)

        val result = depositInfoMapper.toDepositInfo(deposit)

        assertEquals(deposit.id, result.id)
        assertEquals(deposit.duration, result.duration)
        assertEquals(deposit.dateOfOpen, result.dateOfOpen)
        assertEquals(deposit.percent, result.percent)
        assertEquals(deposit.isOutDated, result.isOutDated)
    }
}
