package ru.melowetty.bankservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import ru.melowetty.bankservice.utils.Constants

@Entity
data class Bank(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    
    @Length(min = 9, max = 9, message = "Длина БИК должна быть равна 9 цифрам")
    @Pattern(regexp = Constants.BIK_PATTERN, message = "БИК должен состоять только из цифр!")
    val bic: String,
)
