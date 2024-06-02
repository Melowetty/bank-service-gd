package ru.melowetty.bankservice.service

import ru.melowetty.bankservice.entity.Bank
import ru.melowetty.bankservice.model.CreateBankRequest
import ru.melowetty.bankservice.model.EditBankRequest

interface BankService {
    fun getBankById(id: Long): Bank?
    fun deleteBankById(id: Long)
    fun patchBank(id: Long, fields: Map<String, Any?>): Bank?
    fun editBank(editBankRequest: EditBankRequest): Bank?
    fun createBank(createBankRequest: CreateBankRequest): Bank
    fun getAllBanks(): List<Bank>
    fun getSortedBanksByField(field: String): List<Bank>
}