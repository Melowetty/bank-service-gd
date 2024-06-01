package ru.melowetty.bankservice.utils

import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class ConstantsTest {
    @Test
    fun `BIC check pattern if input data is valid`() {
        val pattern = Constants.BIK_PATTERN.toPattern()

        val data = "123456789"

        assertTrue(pattern.matcher(data).find(), "Правильный БИК определен как неправильный")
    }

    @Test
    fun `BIC check pattern if input data is invalid`() {
        val pattern = Constants.BIK_PATTERN.toPattern()

        val data = "abc354789"

        assertFalse(pattern.matcher(data).find(), "Неправильный БИК определен как правильный")
    }
}