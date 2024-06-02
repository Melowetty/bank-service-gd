package ru.melowetty.bankservice.mapper

import org.springframework.stereotype.Component
import ru.melowetty.bankservice.dto.ClientDto
import ru.melowetty.bankservice.dto.ClientShortDto
import ru.melowetty.bankservice.entity.Client

@Component
class ClientMapper(
    private val depositInfoMapper: DepositInfoMapper
) {
    fun toShortDto(client: Client): ClientShortDto {
        return ClientShortDto(
            id = client.id,
            name = client.name,
            shortName = client.shortName,
            address = client.address,
            organizationalLegalForm = client.organizationalLegalForm,
        )
    }

    fun toDto(client: Client): ClientDto {
        return ClientDto(
            id = client.id,
            name = client.name,
            shortName = client.shortName,
            address = client.address,
            organizationalLegalForm = client.organizationalLegalForm,
            deposits = client.deposits.map { depositInfoMapper.toDepositInfo(it) }
        )
    }
}