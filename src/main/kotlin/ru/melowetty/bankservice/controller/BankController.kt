package ru.melowetty.bankservice.controller

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.melowetty.bankservice.dto.BankDto
import ru.melowetty.bankservice.dto.BankShortDto
import ru.melowetty.bankservice.mapper.BankMapper
import ru.melowetty.bankservice.model.CreateBankRequest
import ru.melowetty.bankservice.model.EditBankRequest
import ru.melowetty.bankservice.service.BankService

@RestController
@RequestMapping("bank")
class BankController(
    private val bankService: BankService,
    private val bankMapper: BankMapper
) {
    @GetMapping
    fun getAllBanks(@RequestParam sortBy: String = ""): List<BankShortDto> {
        return if(sortBy.isNotBlank()) {
            bankService.getSortedBanksByField(field = sortBy).map { bankMapper.toShortDto(it) }
        } else {
            bankService.getAllBanks().map { bankMapper.toShortDto(it) }
        }
    }

    @PostMapping
    fun createBank(@RequestBody createBankRequest: CreateBankRequest): BankDto {
        return bankMapper.toDto(bankService.createBank(createBankRequest))
    }

    @PutMapping("/{id}")
    fun fullEditBank(@PathVariable id: Long, @RequestBody editBankRequest: EditBankRequest): BankDto {
        return bankService.editBank(id, editBankRequest)?.let { bankMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }

    @PatchMapping("/{id}")
    fun partEditBank(@PathVariable id: Long, fields: Map<String, Any?>): BankDto {
        return bankService.patchBank(id, fields)?.let { bankMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable id: Long) {
        return bankService.deleteBankById(id)
    }

    @GetMapping("/{id}")
    fun getBank(@PathVariable id: Long): BankDto {
        return bankService.getBankById(id)?.let { bankMapper.toDto(it) }
            ?: throw EntityNotFoundException("Банк с таким ID не найден!")
    }
}