package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.thebrownfoxx.unfocus.ui.extension.reverseChunked
import com.thebrownfoxx.unfocus.ui.extension.toHhMmSs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun DurationField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
    leadingIcon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(duration.toHhMmSs(separator = "", forceHours = true)))
    }

    LaunchedEffect(duration) {
        textFieldValue =
            textFieldValue.copy(text = duration.toHhMmSs(separator = "", forceHours = true))
    }

    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val length = 6
            val newText = newValue.text
                .filter { it.isDigit() }
                .dropWhile { it == '0' }
                .padStart(length = length, padChar = '0')
            if (newText.length == length) {
                textFieldValue =
                    TextFieldValue(text = newText, selection = TextRange(length, length))
                onDurationChange(newText.parseDuration())
            }
        },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        label = { Text(text = label) },
        singleLine = true,
        visualTransformation = DurationFieldTransformation,
        modifier = modifier.onFocusChanged {
            when {
                it.isFocused -> {
                    val selection = TextRange(
                        start = 0,
                        end = textFieldValue.text.length,
                    )
                    textFieldValue = textFieldValue.copy(selection = selection)
                }

                else -> {
                    textFieldValue = textFieldValue.copy(selection = TextRange(0))
                }
            }
        },
    )
}

private val DurationFieldTransformation = VisualTransformation {
    val timeUnits = it.text.reverseChunked(size = 2, limit = 3)
    val formattedString = timeUnits.reversed().joinToString(":")
    TransformedText(AnnotatedString(formattedString), durationFieldOffsetMapping)
}

private val durationFieldOffsetMapping = object : OffsetMapping {
    override fun originalToTransformed(offset: Int) = 8
    override fun transformedToOriginal(offset: Int) = 6
}

private fun String.parseDuration(): Duration {
    val timeUnits = reverseChunked(size = 2, limit = 3).map { it.toIntOrNull() ?: 0 }

    val hours = timeUnits.getOrElse(2) { 0 }
    val minutes = timeUnits.getOrElse(1) { 0 }
    val seconds = timeUnits.getOrElse(0) { 0 }

    return hours.hours + minutes.minutes + seconds.seconds
}