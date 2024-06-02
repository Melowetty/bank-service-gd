package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.melowetty.bankservice.entity.Deposit

@Repository
interface DepositRepository: CrudRepository<Deposit, Long> {
    fun getDepositByIsOutDated(isOutDated: Boolean = false): List<Deposit>
}