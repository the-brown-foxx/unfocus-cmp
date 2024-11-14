package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import com.thebrownfoxx.unfocus.command.Command
import com.thebrownfoxx.unfocus.command.callMatching

@Composable
fun CommandInput(
    commands: List<Command>,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    var input by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }

    BasicTextField(
        value = input,
        onValueChange = {
            if (it.endsWith("/")) {
                input = "/"
                show = true
            } else {
                input = it
            }
            if (!it.contains("/")) {
                show = false
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall.copy(color = contentColor),
        cursorBrush = SolidColor(contentColor),
        modifier = modifier
            .alpha(if (show) 1f else 0f)
            .onKeyEvent {
                if (it.key.nativeKeyCode == Key.Enter.nativeKeyCode){
                    commands callMatching input
                    input = ""
                    show = false
                    true
                } else false
            },
    )
}