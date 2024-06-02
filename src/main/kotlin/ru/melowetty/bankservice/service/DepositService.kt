package ru.melowetty.bankservice.service

import jakarta.validation.Valid
import ru.melowetty.bankservice.entity.Deposit
import ru.melowetty.bankservice.model.CreateDepositRequest
import ru.melowetty.bankservice.model.EditDepositRequest

interface DepositService {
    fun getAllDeposits(): List<Deposit>
    fun getAllNotOutDatedDeposits(): List<Deposit>
    fun fullEditDeposit(id: Long, @Valid editDepositRequest: EditDepositRequest): Deposit?
    fun partEditDeposit(id: Long, fields: Map<String, Any?>): Deposit?
    fun createDeposit(@Valid createDepositRequest: CreateDepositRequest): Deposit
    fun deleteDeposit(id: Long)
    fun sortDepositsByField(field: String): List<Deposit>
    fun getDepositById(id: Long): Deposit?
}