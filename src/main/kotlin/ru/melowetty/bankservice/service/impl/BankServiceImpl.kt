package ru.melowetty.bankservice.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import org.springframework.validation.annotation.Validated
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.model.CreateBankRequest
import ru.melowetty.bankservice.model.EditBankRequest
import ru.melowetty.bankservice.repository.BankRepository
import ru.melowetty.bankservice.service.BankService
import ru.melowetty.bankservice.utils.ObjectUtils

@Service
@Validated
class BankServiceImpl(
    private val bankRepository: BankRepository,
): BankService {
    override fun getBankById(id: Long): Bank? {
        return bankRepository.findById(id).orElse(null)
    }

    override fun deleteBankById(id: Long) {
        val isExists = bankRepository.existsById(id)
        if (!isExists) throw EntityNotFoundException("Банк с таким ID не найден!")
        bankRepository.deleteById(id)
    }

    override fun patchBank(id: Long, fields: Map<String, Any?>): Bank? {
        val bank = getBankById(id) ?: return null
        fields.forEach { (t, u) ->
            if(t != "id") {
                val field = ReflectionUtils.findField(Bank::class.java, t)
                if (field != null) {
                    field.trySetAccessible()
                    ReflectionUtils.setField(field, bank, u)
                }
            }
        }
        return bankRepository.save(bank)
    }

    override fun editBank(@Valid editBankRequest: EditBankRequest): Bank? {
        val bank = getBankById(editBankRequest.id) ?: return null
        val newBank = bank.copy(
            bic = editBankRequest.bic,
            name = editBankRequest.name,
        )
        return bankRepository.save(newBank)
    }

    override fun createBank(@Valid createBankRequest: CreateBankRequest): Bank {
        val bank = Bank(
            id = 0,
            bic = createBankRequest.bic,
            name = createBankRequest.name,
            deposits = listOf()
        )
        return bankRepository.save(bank)
    }

    override fun getAllBanks(): List<Bank> {
        return bankRepository.findAll().toList()
    }

    override fun getSortedBanksByField(field: String): List<Bank> {
        return ObjectUtils.sortByField(Bank::class, field = field, data = getAllBanks())
            ?: throw RuntimeException("Поле с таким названием не найдено!")
    }

}