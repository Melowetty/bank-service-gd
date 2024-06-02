package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.melowetty.bankservice.entity.Bank

@Repository
interface BankRepository: CrudRepository<Bank, Long>