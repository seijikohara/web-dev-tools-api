package net.relaxism.devtools.valueobjects

import kotlin.math.ceil

data class Pagination<T>(
    val content: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
) {
    val elements: Int
        get() = content.size
    val totalPages: Int
        get() = ceil(totalElements.toDouble() / pageSize.toDouble()).toInt()
}