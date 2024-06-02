package ru.melowetty.bankservice.mapper

import org.springframework.stereotype.Component
import ru.melowetty.bankservice.dto.DepositDto
import ru.melowetty.bankservice.dto.DepositShortDto
import ru.melowetty.bankservice.entity.Deposit

@Component
class DepositMapper(
    private val bankMapper: BankMapper,
    private val clientMapper: ClientMapper,
) {

    fun toShortDto(deposit: Deposit): DepositShortDto {
        return DepositShortDto(
            id = deposit.id,
            bankId = deposit.bank.id,
            clientId = deposit.client.id,
            dateOfOpen = deposit.dateOfOpen,
            percent = deposit.percent,
            duration = deposit.duration,
        )
    }

    fun toDto(deposit: Deposit): DepositDto {
        return DepositDto(
            id = deposit.id,
            bank = bankMapper.toShortDto(deposit.bank),
            client = clientMapper.toShortDto(deposit.client),
            dateOfOpen = deposit.dateOfOpen,
            percent = deposit.percent,
            duration = deposit.duration,
        )
    }
}