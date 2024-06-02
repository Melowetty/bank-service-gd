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
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.entity.Client
import ru.melowetty.bankservice.entity.Deposit
import ru.melowetty.bankservice.model.CreateDepositRequest
import ru.melowetty.bankservice.model.EditDepositRequest
import ru.melowetty.bankservice.model.OrganizationalLegalForm
import ru.melowetty.bankservice.repository.DepositRepository
import ru.melowetty.bankservice.service.impl.DepositServiceImpl
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class DepositServiceTest {
    @InjectMocks
    private lateinit var depositService: DepositServiceImpl

    @Mock
    private lateinit var depositRepository: DepositRepository
    @Mock
    private lateinit var bankService: BankService
    @Mock
    private lateinit var clientService: ClientService

    @Test
    fun `get all deposits`() {
        val expected = listOf(
            Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12),
            Deposit(id = 2, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 6, duration = 24)
        )

        Mockito.`when`(depositRepository.findAll()).thenReturn(expected)

        val actual = depositService.getAllDeposits()

        Assertions.assertEquals(expected, actual, "Списки всех депозитов не совпадают")
    }

    @Test
    fun `get all not outdated deposits`() {
        val expected = listOf(
            Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12, isOutDated = false),
            Deposit(id = 2, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 6, duration = 24, isOutDated = false)
        )

        Mockito.`when`(depositRepository.getDepositByIsOutDated(isOutDated = false)).thenReturn(expected)

        val actual = depositService.getAllNotOutDatedDeposits()

        Assertions.assertEquals(expected, actual, "Списки всех неистекших депозитов не совпадают")
    }

    @Test
    fun `full edit deposit when it exists`() {
        val date = LocalDateTime.now()
        val before = Deposit(id = 1, client = getClient(), bank = getBank(), dateOfOpen = date, percent = 5, duration = 12)
        val after = Deposit(id = 1, client = getClient(), bank = getBank(), dateOfOpen = date, percent = 7, duration = 18)

        Mockito.`when`(depositRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(bankService.getBankById(getBank().id)).thenReturn(getBank())
        Mockito.`when`(clientService.getClientById(getClient().id)).thenReturn(getClient())
        Mockito.`when`(depositRepository.save(after)).thenReturn(after)

        val editRequest = EditDepositRequest(clientId = 1, bankId = 1, percent = after.percent, duration = after.duration)

        val actual = depositService.fullEditDeposit(1, editRequest)

        Assertions.assertEquals(after, actual, "Депозит не изменился после полного редактирования")
    }

    @Test
    fun `full edit deposit when it does not exist`() {
        Mockito.`when`(depositRepository.findById(1)).thenReturn(Optional.empty())

        val editRequest = EditDepositRequest(clientId = 1, bankId = 1, percent = 7, duration = 18)

        val actual = depositService.fullEditDeposit(1, editRequest)

        Assertions.assertNull(actual, "Найден депозит, которого не существует")
    }

    @Test
    fun `part edit deposit`() {
        val date = LocalDateTime.now()
        val before = Deposit(id = 1, client = getClient(), bank = getBank(), dateOfOpen = date, percent = 5, duration = 12)
        val after = Deposit(id = 1, client = getClient(), bank = getBank(), dateOfOpen = date, percent = 7, duration = 12)

        Mockito.`when`(depositRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(depositRepository.save(after)).thenReturn(after)

        val actual = depositService.partEditDeposit(1, mapOf("percent" to 7))

        Assertions.assertEquals(after, actual, "Депозит не изменился после частичного редактирования")
    }

    @Test
    fun `create deposit`() {
        val expected = Deposit(id = 1, client = getClient(), bank = getBank(), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12)
        val createDepositRequest = CreateDepositRequest(clientId = 1, bankId = 1, percent = expected.percent, duration = expected.duration)

        Mockito.`when`(depositRepository.save(Mockito.any())).thenReturn(expected)
        Mockito.`when`(bankService.getBankById(getBank().id)).thenReturn(getBank())
        Mockito.`when`(clientService.getClientById(getClient().id)).thenReturn(getClient())

        val actual = depositService.createDeposit(createDepositRequest)

        Assertions.assertEquals(expected, actual, "Депозит не добавился после создания")
    }

    @Test
    fun `delete deposit when it exists`() {
        Mockito.`when`(depositRepository.existsById(1)).thenReturn(true)

        depositService.deleteDeposit(1)

        Mockito.verify(depositRepository, Mockito.times(1)).deleteById(1)
    }

    @Test
    fun `delete deposit when it does not exist`() {
        Mockito.`when`(depositRepository.existsById(1)).thenReturn(false)

        assertThrows<EntityNotFoundException> {
            depositService.deleteDeposit(1)
        }

        Mockito.verify(depositRepository, Mockito.never()).deleteById(1)
    }

    @Test
    fun `sort deposits by field`() {
        val expected = listOf(
            Deposit(id = 2, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 6, duration = 24),
            Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12)
        )

        Mockito.`when`(depositRepository.getDepositByIsOutDated(isOutDated = false)).thenReturn(expected)

        val actual = depositService.sortDepositsByField("percent")

        Assertions.assertEquals(expected.sortedBy { it.percent }, actual, "Список депозитов не отсортирован по полю percent")
    }

    @Test
    fun `sort deposits by nonexistent field`() {
        val expected = listOf(
            Deposit(id = 2, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 6, duration = 24),
            Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12)
        )

        Mockito.`when`(depositRepository.getDepositByIsOutDated(isOutDated = false)).thenReturn(expected)

        assertThrows<RuntimeException> {
            depositService.sortDepositsByField("nonexistentField")
        }
    }

    @Test
    fun `get client by id when it is exists`() {
        val expected = Deposit(id = 1, client = Mockito.mock(Client::class.java), bank = Mockito.mock(Bank::class.java), dateOfOpen = LocalDateTime.now(), percent = 5, duration = 12)

        Mockito.`when`(depositRepository.findById(1)).thenReturn(Optional.of(expected))

        val actual = depositService.getDepositById(1)

        Assertions.assertEquals(expected, actual, "Депозит который должен быть не найден")
    }

    @Test
    fun `get bank by id when it is no exists`() {
        Mockito.`when`(depositRepository.findById(Mockito.any())).thenReturn(Optional.empty())

        val actual = depositService.getDepositById(1)

        Assertions.assertNull(actual, "Депозит который не существует найден")
    }

    companion object {
        fun getBank(): Bank {
            return Bank(
                id = 1,
                bic = "123456789",
                name = "Tinkoff"
            )
        }

        fun getClient(): Client {
            return Client(
                id = 1,
                name = "Ozon",
                shortName = "Ozon",
                address = "Moscow",
                organizationalLegalForm = OrganizationalLegalForm.AO
            )
        }
    }
}
