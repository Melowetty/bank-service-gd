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
import ru.melowetty.bankservice.model.CreateBankRequest
import ru.melowetty.bankservice.model.EditBankRequest
import ru.melowetty.bankservice.repository.BankRepository
import ru.melowetty.bankservice.service.impl.BankServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class BankServiceTest {
    @InjectMocks
    private lateinit var bankService: BankServiceImpl
    @Mock
    private lateinit var bankRepository: BankRepository

    @Test
    fun `normal get all banks`() {
        val expected = listOf(
            Bank(id = 1, bic = "123456789", name = "Tinkoff", deposits = listOf()),
            Bank(id = 2, bic = "234532546", name = "Sber", deposits = listOf())
        )

        Mockito.`when`(bankRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        val actual = bankService.getAllBanks()

        Assertions.assertEquals(expected, actual, "Списки со всеми банками не равны изначальным банкам")
    }

    @Test
    fun `get sorted banks by bic`() {
        val expected = listOf(
            Bank(id = 2, bic = "123456789", name = "Sber", deposits = listOf()),
            Bank(id = 1, bic = "234532546", name = "Tinkoff", deposits = listOf()),
        )
        Mockito.`when`(bankRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        val actual = bankService.getSortedBanksByField(field = "bic")

        Assertions.assertEquals(expected, actual, "Списки с отсортированными банками по полю bic не равны")
    }

    @Test
    fun `get sorted banks by not exists field`() {
        val expected = listOf(
            Bank(id = 2, bic = "123456789", name = "Sber", deposits = listOf()),
            Bank(id = 1, bic = "234532546", name = "Tinkoff", deposits = listOf()),
        )
        Mockito.`when`(bankRepository.findAll()).thenReturn(
            expected.stream().toList()
        )

        assertThrows<RuntimeException> {
            bankService.getSortedBanksByField(field = "nonexistsfield")
        }
    }

    @Test
    fun `get bank by id when it is exists`() {
        val expected = Bank(id = 1, bic = "234532546", name = "Tinkoff", deposits = listOf())

        Mockito.`when`(bankRepository.findById(1)).thenReturn(Optional.of(expected))

        val actual = bankService.getBankById(1)

        Assertions.assertEquals(expected, actual, "Банк который должен быть не найден")
    }

    @Test
    fun `get bank by id when it is no exists`() {
        Mockito.`when`(bankRepository.findById(Mockito.any())).thenReturn(Optional.empty())

        val actual = bankService.getBankById(1)

        Assertions.assertNull(actual, "Банк который не существует найден")
    }

    @Test
    fun `delete bank by id, which exists`() {
        Mockito.`when`(bankRepository.existsById(1)).thenReturn(true)
        bankService.deleteBankById(1)
        Mockito.verify(bankRepository, Mockito.times(1)).deleteById(1)
    }

    @Test
    fun `delete bank by id, which not exists`() {
        Mockito.`when`(bankRepository.existsById(1)).thenReturn(false)
        assertThrows<EntityNotFoundException> {
            bankService.deleteBankById(1)
        }
        Mockito.verify(bankRepository, Mockito.never()).deleteById(1)
    }

    @Test
    fun `patch bank`() {
        val before = Bank(id = 1, bic = "234532546", name = "Tinkoff", deposits = listOf())
        val after = Bank(id = 1, bic = "234532546", name = "Sber", deposits = listOf())
        Mockito.`when`(bankRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(bankRepository.save(after)).thenReturn(after)
        val actual = bankService.patchBank(1, mapOf("name" to "Sber"))
        Assertions.assertEquals(actual, after, "Банк не изменился после запроса на частичное изменение")
    }

    @Test
    fun `create bank`() {
        val expected = Bank(id = 1, bic = "234532546", name = "Tinkoff", deposits = listOf())
        val createBankRequest = CreateBankRequest(
            bic = "234532546",
            name = "Tinkoff"
        )
        Mockito.`when`(bankRepository.save(expected.copy(id = 0))).thenReturn(expected)
        val actual = bankService.createBank(createBankRequest)
        Assertions.assertEquals(expected, actual, "Банк не добавился после запроса на добавление")
    }

    @Test
    fun `edit exists bank`() {
        val request = EditBankRequest(id = 1, "Sber", bic = "234532546")
        val before = Bank(id = 1, bic = "123456789", name = "Tinkoff", deposits = listOf())
        val after = Bank(id = 1, bic = "234532546", name = "Sber", deposits = listOf())
        Mockito.`when`(bankRepository.findById(1)).thenReturn(Optional.of(before))
        Mockito.`when`(bankRepository.save(after)).thenReturn(after)
        val actual = bankService.editBank(request)
        Assertions.assertEquals(actual, after, "Банк не изменился после запроса на изменение")
    }

    @Test
    fun `edit non exists bank`() {
        val request = EditBankRequest(id = 1, "Sber", bic = "234532546")
        Mockito.`when`(bankRepository.findById(1)).thenReturn(Optional.empty())
        val actual = bankService.editBank(request)
        Assertions.assertNull(actual, "Банк нашелся в запросе на изменение, хотя его не существует")
    }
}