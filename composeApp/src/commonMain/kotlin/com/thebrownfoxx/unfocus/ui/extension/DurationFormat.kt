package com.thebrownfoxx.unfocus.ui.extension

import kotlin.time.Duration

fun Duration.toMmSs(separator: String = ":") = toComponents { minutes, seconds, _ ->
    "${minutes.toTwoDigits()}$separator${seconds.toTwoDigits()}"
}

fun Duration.toHhMmSs(separator: String = ":", forceHours: Boolean = false) =
    toComponents { hours, minutes, seconds, _ ->
        val formattedHours = if (hours > 0 || forceHours) "${hours.toTwoDigits()}$separator" else ""
        "$formattedHours${minutes.toTwoDigits()}$separator${seconds.toTwoDigits()}"
    }

fun Number.toTwoDigits() = toString().padStart(2, '0')