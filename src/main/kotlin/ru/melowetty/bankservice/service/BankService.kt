package ru.melowetty.bankservice.service

import jakarta.validation.Valid
import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.model.CreateBankRequest
import ru.melowetty.bankservice.model.EditBankRequest

interface BankService {
    fun getBankById(id: Long): Bank?
    fun deleteBankById(id: Long)
    fun patchBank(id: Long, fields: Map<String, Any?>): Bank?
    fun editBank(id: Long, @Valid editBankRequest: EditBankRequest): Bank?
    fun createBank(@Valid createBankRequest: CreateBankRequest): Bank
    fun getAllBanks(): List<Bank>
    fun getSortedBanksByField(field: String): List<Bank>
}