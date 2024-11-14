package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent

@Composable
fun CommandInput(
    command: String,
    onCommandChange: (String) -> Unit,
    commands: List<Command>,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = command,
        onValueChange = onCommandChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall.copy(color = contentColor),
        cursorBrush = SolidColor(contentColor),
        modifier = modifier
            .onKeyEvent {
                if (it.key.nativeKeyCode == Key.Enter.nativeKeyCode){
                    commands match command
                    onCommandChange("")
                    true
                } else false
            },
    )
}

private infix fun List<Command>.match(command: String) {
    for ((regex, onMatch) in this) {
        if (command matches Regex(".*/$regex")) {
            onMatch()
        }
    }
}

data class Command(
    val regex: Regex,
    val onMatch: () -> Unit,
) {
    constructor(
        string: String,
        onMatch: () -> Unit,
    ) : this(
        regex = Regex(Regex.escape(string)),
        onMatch = onMatch,
    )
}