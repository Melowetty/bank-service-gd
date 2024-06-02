package ru.melowetty.bankservice.utils

import org.springframework.util.ReflectionUtils
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

        fun <T> changeFields(cls: Class<T>, fields: Map<String, Any?>, target: T) {
            fields.forEach { (t, u) ->
                if(t != "id") {
                    val field = ReflectionUtils.findField(cls, t)
                    if (field != null) {
                        field.trySetAccessible()
                        ReflectionUtils.setField(field, target, u)
                    }
                }
            }
        }
    }
}