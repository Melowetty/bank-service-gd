package ru.melowetty.bankservice.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.melowetty.bankservice.repository.DepositRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class DepositOutDatingCheckingWorker(
    private val depositRepository: DepositRepository,
) {
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    fun outDatingDepositChecking() {
        val currentDate = LocalDateTime.now()
        val deposits = depositRepository.getDepositByIsOutDated()
        deposits.map {
            val difference = ChronoUnit.MONTHS.between(currentDate, it.dateOfOpen)
            it.copy(isOutDated = difference > it.duration)
        }.filter {
            it.isOutDated
        }.forEach {
            depositRepository.save(it)
        }
    }
}