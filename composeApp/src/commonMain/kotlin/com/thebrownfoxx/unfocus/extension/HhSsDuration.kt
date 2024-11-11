package com.thebrownfoxx.unfocus.extension

import kotlin.time.Duration

fun Duration.toHhSs() = toComponents { minutes, seconds, _ ->
    "${minutes.toTwoDigits()} ${seconds.toTwoDigits()}"
}

private fun Number.toTwoDigits() = toString().padStart(2, '0')