package ru.melowetty.bankservice.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import ru.melowetty.bankservice.utils.Constants

data class CreateBankRequest(
    @NotBlank(message = "Название банка не может быть пустым")
    @Length(min = 2, max = 256, message = "Название банка должно быть длинее 1 символа и не больше 256 символов")
    val name: String,

    @Length(min = 9, max = 9, message = "Длина БИК должна быть равна 9 цифрам")
    @Pattern(regexp = Constants.BIK_PATTERN, message = "БИК должен состоять только из цифр!")
    val bic: String,
)
