package net.relaxism.devtools.valueobjects

import kotlin.math.ceil

data class Pagination<T>(
    val contents: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalElements: Int,
) {
    val elements: Int
        get() = contents.size
    val totalPages: Int
        get() = ceil(totalElements.toDouble() / pageSize.toDouble()).toInt()
}