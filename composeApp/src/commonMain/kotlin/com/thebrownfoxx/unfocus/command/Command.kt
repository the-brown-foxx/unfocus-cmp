package com.thebrownfoxx.unfocus.command

data class Command(
    val keyword: String,
    val argumentCount: Int = 0,
    val onMatch: (List<String>) -> Unit,
) {
    infix fun callOnMatch(input: String) {
        val keywordCorrect = input.startsWith("/${this.keyword}")
        val arguments = input
            .removePrefix("/$keyword")
            .split(' ')
            .filter { it.isNotBlank() }
        if (keywordCorrect && arguments.size == argumentCount) {
            onMatch(arguments)
        }
    }
}

infix fun List<Command>.callMatching(input: String) {
    for (command in this) {
        command callOnMatch input
    }
}