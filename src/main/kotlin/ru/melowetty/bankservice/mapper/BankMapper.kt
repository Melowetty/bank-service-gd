package ru.melowetty.bankservice.mapper

import org.springframework.stereotype.Component
import ru.melowetty.bankservice.dto.BankDto
import ru.melowetty.bankservice.dto.BankShortDto
import ru.melowetty.bankservice.entity.Bank

@Component
class BankMapper(
    private val depositInfoMapper: DepositInfoMapper
) {
    fun toDto(bank: Bank): BankDto {
        return BankDto(
            id = bank.id,
            name = bank.name,
            bic = bank.bic,
            deposits = bank.deposits.map { depositInfoMapper.toDepositInfo(it) }
        )
    }

    fun toShortDto(bank: Bank): BankShortDto {
        return BankShortDto(
            id = bank.id,
            name = bank.name,
            bic = bank.bic,
        )
    }
}