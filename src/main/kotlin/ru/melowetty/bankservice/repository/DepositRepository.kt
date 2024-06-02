package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.bankservice.entity.Deposit

interface DepositRepository: CrudRepository<Deposit, Long>