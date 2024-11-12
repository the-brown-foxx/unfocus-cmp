package com.thebrownfoxx.unfocus

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CodeInput(
    code: String,
    onCodeChange: (String) -> Unit,
    onTestModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = code,
        onValueChange = {
            when {
                it.endsWith("test") -> {
                    onTestModeChange(true)
                    onCodeChange("")
                }

                it.endsWith("reset") -> {
                    onTestModeChange(false)
                    onCodeChange("")
                }

                else -> onCodeChange(it)
            }
        },
        singleLine = true,
        modifier = modifier.size(16.dp),
    )
}