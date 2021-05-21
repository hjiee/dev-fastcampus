package com.hjiee.fastcampus

import kotlin.reflect.KClass

data class Contents(
        val title: String,
        val kClass: KClass<*>
) {
        var isTop : Boolean = false
}