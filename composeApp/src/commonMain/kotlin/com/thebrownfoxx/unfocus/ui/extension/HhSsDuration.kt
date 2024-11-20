package com.thebrownfoxx.unfocus.ui.extension

import kotlin.time.Duration

fun Duration.toHhSs(separator: String = ":") = toComponents { minutes, seconds, _ ->
    "${minutes.toTwoDigits()}$separator${seconds.toTwoDigits()}"
}

fun Number.toTwoDigits() = toString().padStart(2, '0')