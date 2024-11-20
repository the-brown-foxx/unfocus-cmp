package com.thebrownfoxx.unfocus.ui.extension

import androidx.compose.ui.Modifier

fun Modifier.onlyIf(condition: Boolean, modifier: () -> Modifier) = if (condition) {
    then(modifier())
} else {
    this
}

fun <T : Any> Modifier.onlyIfNotNull(value: T?, modifier: (T) -> Modifier) =
    if (value != null) {
        then(modifier(value))
    } else {
        this
    }