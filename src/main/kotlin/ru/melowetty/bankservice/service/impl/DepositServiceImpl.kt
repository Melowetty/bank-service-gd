package ru.melowetty.bankservice.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.melowetty.bankservice.entity.Deposit
import ru.melowetty.bankservice.model.CreateDepositRequest
import ru.melowetty.bankservice.model.EditDepositRequest
import ru.melowetty.bankservice.repository.DepositRepository
import ru.melowetty.bankservice.service.BankService
import ru.melowetty.bankservice.service.ClientService
import ru.melowetty.bankservice.service.DepositService
import ru.melowetty.bankservice.utils.ObjectUtils
import java.time.LocalDateTime

@Service
@Validated
class DepositServiceImpl(
    private val depositRepository: DepositRepository,
    private val bankService: BankService,
    private val clientService: ClientService,
): DepositService {
    override fun getAllDeposits(): List<Deposit> {
        return depositRepository.findAll().toList()
    }

    override fun getAllNotOutDatedDeposits(): List<Deposit> {
        return depositRepository.getDepositByIsOutDated(isOutDated = false)
    }

    override fun fullEditDeposit(id: Long, @Valid editDepositRequest: EditDepositRequest): Deposit? {
        val deposit = getDepositById(id) ?: return null
        val client = clientService.getClientById(editDepositRequest.clientId)
            ?: throw EntityNotFoundException("Клиент с таким ID не найден!")
        val bank = bankService.getBankById(editDepositRequest.bankId)
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
        val newDeposit = deposit.copy(
            client = client,
            bank = bank,
            percent = editDepositRequest.percent,
            duration = editDepositRequest.duration,
        )
        return depositRepository.save(newDeposit)
    }

    override fun partEditDeposit(id: Long, fields: Map<String, Any?>): Deposit? {
        val deposit = getDepositById(id) ?: return null
        ObjectUtils.changeFields(Deposit::class.java, fields = fields, target = deposit)
        return depositRepository.save(deposit)
    }

    override fun createDeposit(@Valid createDepositRequest: CreateDepositRequest): Deposit {
        val client = clientService.getClientById(createDepositRequest.clientId)
            ?: throw EntityNotFoundException("Клиент с таким ID не найден!")
        val bank = bankService.getBankById(createDepositRequest.bankId)
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
        val deposit = Deposit(
            id = 0,
            client = client,
            bank = bank,
            dateOfOpen = LocalDateTime.now(),
            percent = createDepositRequest.percent,
            duration = createDepositRequest.duration,
        )
        return depositRepository.save(deposit)
    }

    override fun deleteDeposit(id: Long) {
        if (!depositRepository.existsById(id)) {
            throw EntityNotFoundException("Депозит с таким ID не найден!")
        }
        depositRepository.deleteById(id)
    }

    override fun sortDepositsByField(field: String): List<Deposit> {
        return ObjectUtils.sortByField(Deposit::class, field = field, data = getAllNotOutDatedDeposits())
            ?: throw RuntimeException("Поле с таким названием не найдено!")
    }

    override fun getDepositById(id: Long): Deposit? {
        return depositRepository.findById(id).orElse(null)
    }
}