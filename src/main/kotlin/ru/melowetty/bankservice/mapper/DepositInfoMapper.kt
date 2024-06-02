package ru.melowetty.bankservice.mapper

import org.springframework.stereotype.Component
import ru.melowetty.bankservice.dto.DepositInfo
import ru.melowetty.bankservice.entity.Deposit

@Component
class DepositInfoMapper {
    fun toDepositInfo(deposit: Deposit): DepositInfo {
        return DepositInfo(
            id = deposit.id,
            duration = deposit.duration,
            dateOfOpen = deposit.dateOfOpen,
            percent = deposit.percent,
            isOutDated = deposit.isOutDated
        )
    }
}