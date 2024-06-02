package ru.melowetty.bankservice.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class ObjectUtils {
    companion object {
        fun <T: Any> sortByField(cls: KClass<T>, field: String, data: List<T>): List<T>? {
            val property = cls.memberProperties.find { it.name == field } ?: return null
            try {
                return data.sortedBy {
                    property.get(it) as Comparable<Any>
                }
            } catch (e: Exception) {
                return null
            }
        }
    }
}