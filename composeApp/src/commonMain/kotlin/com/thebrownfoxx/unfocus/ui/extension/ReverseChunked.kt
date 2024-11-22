package com.thebrownfoxx.unfocus.ui.extension

fun String.reverseChunked(size: Int, limit: Int = 0): List<String> {
    val strings = mutableListOf<String>()
    var remainingString = this
    var chunksLeft = limit
    while ((chunksLeft > 0 || limit <= 0) && remainingString.isNotEmpty()) {
        strings.add(remainingString.takeLast(size))
        remainingString = remainingString.dropLast(size)
        chunksLeft--
    }
    return strings.toList()
}