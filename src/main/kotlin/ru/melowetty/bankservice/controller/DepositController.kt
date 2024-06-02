package ru.melowetty.bankservice.controller

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.melowetty.bankservice.dto.DepositDto
import ru.melowetty.bankservice.dto.DepositShortDto
import ru.melowetty.bankservice.mapper.DepositMapper
import ru.melowetty.bankservice.model.CreateDepositRequest
import ru.melowetty.bankservice.model.EditDepositRequest
import ru.melowetty.bankservice.service.DepositService

@RestController
@RequestMapping("deposit")
class DepositController(
    private val depositService: DepositService,
    private val depositMapper: DepositMapper
) {

    @GetMapping
    fun getAllNotOutDatedDeposits(@RequestParam sortBy: String = ""): List<DepositShortDto> {
        return if (sortBy.isNotBlank()) {
            depositService.sortDepositsByField(field = sortBy).map { depositMapper.toShortDto(it) }
        } else {
            depositService.getAllNotOutDatedDeposits().map { depositMapper.toShortDto(it) }
        }
    }

    @GetMapping("/all")
    fun getAllDeposits(): List<DepositShortDto> {
        return depositService.getAllDeposits().map { depositMapper.toShortDto(it) }
    }

    @PostMapping
    fun createDeposit(@RequestBody createDepositRequest: CreateDepositRequest): DepositDto {
        return depositMapper.toDto(depositService.createDeposit(createDepositRequest))
    }

    @PutMapping("/{id}")
    fun fullEditDeposit(@PathVariable id: Long, @RequestBody editDepositRequest: EditDepositRequest): DepositDto {
        return depositService.fullEditDeposit(id, editDepositRequest)?.let { depositMapper.toDto(it) }
            ?: throw EntityNotFoundException("Депозит с таким ID не найден!")
    }

    @PatchMapping("/{id}")
    fun partEditDeposit(@PathVariable id: Long, @RequestBody fields: Map<String, Any?>): DepositDto {
        return depositService.partEditDeposit(id, fields)?.let { depositMapper.toDto(it) }
            ?: throw EntityNotFoundException("Депозит с таким ID не найден!")
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDeposit(@PathVariable id: Long) {
        depositService.deleteDeposit(id)
    }

    @GetMapping("/{id}")
    fun getDeposit(@PathVariable id: Long): DepositDto {
        return depositService.getDepositById(id)?.let { depositMapper.toDto(it) }
            ?: throw EntityNotFoundException("Депозит с таким ID не найден!")
    }
}
