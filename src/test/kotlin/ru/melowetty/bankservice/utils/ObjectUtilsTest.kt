package ru.melowetty.bankservice.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ObjectUtilsTest {
    @Test
    fun `sort objects by exists field`() {
        val originalList = listOf(
            TestClass(id = 2, name = "Test2"),
            TestClass(id = 1, name = "Test1"),
        )

        val expected = listOf(
            TestClass(id = 1, name = "Test1"),
            TestClass(id = 2, name = "Test2"),
        )

        val actual = ObjectUtils.sortByField(TestClass::class, field = "id", data = originalList)

        Assertions.assertEquals(expected, actual, "Отсортирвоанные списки по ID не равны")
    }

    @Test
    fun `sort objects by non exists field`() {
        val originalList = listOf(
            TestClass(id = 2, name = "Test2"),
            TestClass(id = 1, name = "Test1"),
        )

        val result = ObjectUtils.sortByField(TestClass::class, field = "nonexistfield", data = originalList)

        Assertions.assertNull(result, "Вернулся отсортированный список по несуществующему полю, хотя должен был вернуться null")
    }

    @Test
    fun `change object fields`() {
        val expected = TestClass(
            id = 1,
            name = "Test2"
        )

        val obj = TestClass(
            id = 1,
            name = "Test"
        )

        val fields = mapOf("name" to "Test2")

        ObjectUtils.changeFields(TestClass::class.java, fields = fields, target = obj)

        Assertions.assertEquals(expected, obj, "Объект не изменился после изменений")
    }


    data class TestClass(
        val id: Long,
        val name: String,
    )
}