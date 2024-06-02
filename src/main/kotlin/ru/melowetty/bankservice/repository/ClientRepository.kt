package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.bankservice.entity.Client

interface ClientRepository: CrudRepository<Client, Long>