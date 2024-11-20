package com.thebrownfoxx.unfocus.ui.screen.timer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccessibilityNew
import androidx.compose.material.icons.twotone.CenterFocusStrong
import androidx.compose.material.icons.twotone.EmojiFoodBeverage
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material.icons.twotone.Timelapse
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.component.DurationField
import com.thebrownfoxx.unfocus.ui.component.PhaseQueueIndicator
import com.thebrownfoxx.unfocus.ui.component.Spacer
import com.thebrownfoxx.unfocus.ui.extension.bottomPadding
import com.thebrownfoxx.unfocus.ui.extension.horizontalPadding
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ShownConfigurationSheetState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.eye_break
import unfocus.composeapp.generated.resources.eye_breaks
import unfocus.composeapp.generated.resources.focus
import unfocus.composeapp.generated.resources.full_rest
import unfocus.composeapp.generated.resources.save
import unfocus.composeapp.generated.resources.sit_break
import unfocus.composeapp.generated.resources.sit_breaks
import unfocus.composeapp.generated.resources.stride
import kotlin.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationSheet(
    state: ConfigurationSheetState,
    eventHandler: ConfigurationSheetEventHandler,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val onSave = {
        coroutineScope.launch {
            sheetState.hide()
            eventHandler.onSaveConfiguration()
        }
    }

    if (state is ShownConfigurationSheetState) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = eventHandler.onHideConfigurationSheet,
            modifier = modifier,
        ) {
            Configurator(
                state = state,
                eventHandler = eventHandler.copy(onSaveConfiguration = { onSave() }),
            )
        }
    }
}

@Composable
private fun Configurator(
    state: ShownConfigurationSheetState,
    eventHandler: ConfigurationSheetEventHandler,
) {
    with(state) {
        with(eventHandler) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(sheetPadding.bottomPadding),
            ) {
                PhaseQueueIndicator(
                    phaseIndex = 0,
                    phaseProgress = 0f,
                    phaseQueue = phaseQueue,
                    contentPadding = sheetPadding.horizontalPadding,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                FieldRow {
                    EyeBreaksSlider(
                        value = eyeBreaks,
                        onValueChange = onEyeBreaksChange,
                    )
                    SitBreaksSlider(
                        value = sitBreaks,
                        onValueChange = onSitBreaksChange,
                    )
                }
                FieldRow {
                    FocusField(
                        duration = focusDuration,
                        onDurationChange = onFocusDurationChange,
                    )
                    EyeBreakField(
                        duration = eyeBreakDuration,
                        onDurationChange = onEyeBreakDurationChange,
                    )
                }
                FieldRow {
                    SitBreakField(
                        duration = sitBreakDuration,
                        onDurationChange = onSitBreakDurationChange,
                    )
                    FullRestField(
                        duration = fullRestDuration,
                        onDurationChange = onFullRestDurationChange,
                    )
                }
                FieldRow {
                    StrideDurationField(
                        duration = strideDuration,
                        onDurationChange = onStrideDurationChange,
                    )
                    SaveButton(onClick = onSaveConfiguration)
                }
            }
        }
    }
}

@Composable
private fun FieldRow(content: @Composable RowScope.() -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = sheetPadding),
    )
}

@Composable
private fun RowScope.EyeBreaksSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
) {
    FrequencySlider(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(Res.string.eye_breaks),
    )
}

@Composable
private fun RowScope.SitBreaksSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
) {
    FrequencySlider(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(Res.string.sit_breaks),
    )
}

@Composable
private fun RowScope.FrequencySlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
) {
    val maxValue = 3

    Column(modifier = Modifier.weight(1f)) {
        Text(text = label)
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..maxValue.toFloat(),
            steps = maxValue - 1,
        )
    }
}

@Composable
private fun RowScope.FocusField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    WeightedDurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.CenterFocusStrong,
        label = stringResource(Res.string.focus),
    )
}

@Composable
private fun RowScope.EyeBreakField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    WeightedDurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.VisibilityOff,
        label = stringResource(Res.string.eye_break),
    )
}

@Composable
private fun RowScope.SitBreakField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    WeightedDurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.AccessibilityNew,
        label = stringResource(Res.string.sit_break),
    )
}

@Composable
private fun RowScope.FullRestField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    WeightedDurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.EmojiFoodBeverage,
        label = stringResource(Res.string.full_rest),
    )
}

@Composable
private fun RowScope.StrideDurationField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    WeightedDurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.Timelapse,
        label = stringResource(Res.string.stride),
    )
}

@Composable
private fun RowScope.WeightedDurationField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
    leadingIcon: ImageVector,
    label: String,
) {
    DurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = leadingIcon,
        label = label,
        modifier = Modifier.weight(1f),
    )
}

@Composable
private fun RowScope.SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f),
    ) {
        Icon(imageVector = Icons.TwoTone.Save, contentDescription = null)
        Spacer(width = 8.dp)
        Text(text = stringResource(Res.string.save))
    }
}

private val sheetPadding = 32.dp