package ru.melowetty.bankservice.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.melowetty.bankservice.entity.Client

@Repository
interface ClientRepository: CrudRepository<Client, Long>