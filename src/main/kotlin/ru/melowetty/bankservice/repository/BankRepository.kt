package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.bankservice.entity.Bank

interface BankRepository: CrudRepository<Bank, Long>