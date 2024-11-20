package com.thebrownfoxx.unfocus.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.thebrownfoxx.unfocus.ui.extension.toMMSs
import kotlin.time.Duration
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
    var textFieldValue by remember(duration) { mutableStateOf(TextFieldValue(duration.toMMSs())) }

    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val newText = newValue.text
            if (
                newText.all { it.isDigit() || it == ':' } &&
                newText.count { it == ':' } <= 1 &&
                newText.length <= 6
            ) {
                textFieldValue = newValue
            }
        },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        label = { Text(text = label) },
        singleLine = true,
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
                    onDurationChange(textFieldValue.text.parseDuration())
                }
            }
        },
    )
}

private fun String.parseDuration(): Duration {
    val (minutes, seconds) = if (contains(':')) split(':') else {
        val seconds = takeLast(2)
        val minutes = removeSuffix(seconds)
        listOf(minutes, seconds)
    }.map { it.toIntOrNull() ?: 0 }

    return minutes.minutes + seconds.seconds
}

//@Composable
//fun DurationField(
//    duration: Duration,
//    onDurationChange: (Duration) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val (minutes, seconds) = duration.toComponents { minutes, seconds, _ ->
//        minutes to seconds
//    }
//
//    Row(modifier = modifier) {
//        DurationComponentField(
//            value = minutes.toString(),
//            onValueChange = {
//                val newMinutes = it.toIntOrNull()
//                if (newMinutes != null) {
//                    onDurationChange(newMinutes.minutes + seconds.seconds)
//                }
//            },
//            roundedCorners = RoundedCorners(Side.Start),
//        )
//        Spacer(width = 8.dp)
//        DurationComponentField(
//            value = seconds.toTwoDigits(),
//            onValueChange = {
//                val newSeconds = it.toIntOrNull()
//                if (newSeconds != null) {
//                    onDurationChange(minutes.minutes + newSeconds.seconds)
//                }
//            },
//            roundedCorners = RoundedCorners(Side.End),
//        )
//    }
//}
//
//@Composable
//private fun DurationComponentField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    roundedCorners: RoundedCorners,
//    modifier: Modifier = Modifier,
//) {
//    var textFieldValue by remember(value) { mutableStateOf(TextFieldValue(value)) }
//
//    TextField(
//        value = textFieldValue,
//        onValueChange = { onValueChange(it.text) },
//        shape = roundedCorners.toShape(),
//        singleLine = true,
//        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
//        colors = TextFieldDefaults.colors(
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        modifier = modifier
//            .width(96.dp)
//            .onFocusChanged {
//                textFieldValue = when {
//                    it.isFocused -> {
//                        val selection = TextRange(
//                            start = 0,
//                            end = value.length,
//                        )
//                        textFieldValue.copy(selection = selection)
//                    }
//
//                    else -> textFieldValue.copy(selection = TextRange(0))
//                }
//            },
//    )
//}