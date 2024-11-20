package com.thebrownfoxx.unfocus.ui.screen.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccessibilityNew
import androidx.compose.material.icons.twotone.CenterFocusStrong
import androidx.compose.material.icons.twotone.EmojiFoodBeverage
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.unfocus.ui.component.DurationField
import com.thebrownfoxx.unfocus.ui.component.Spacer
import com.thebrownfoxx.unfocus.ui.extension.PaddingSide
import com.thebrownfoxx.unfocus.ui.extension.paddingExcept
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetEventHandler
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ConfigurationSheetState
import com.thebrownfoxx.unfocus.ui.screen.timer.state.ShownConfigurationSheetState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.eye_break
import unfocus.composeapp.generated.resources.focus
import unfocus.composeapp.generated.resources.full_rest
import unfocus.composeapp.generated.resources.save
import unfocus.composeapp.generated.resources.sit_break
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
                modifier = Modifier.padding(32.dp.paddingExcept(PaddingSide.Top)),
            ) {
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
                SaveButton(onClick = onSaveConfiguration)
            }
        }
    }
}

@Composable
private fun FieldRow(content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth(),
    ) {
        content()
    }
}

@Composable
private fun FocusField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    DurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.CenterFocusStrong,
        label = stringResource(Res.string.focus),
    )
}

@Composable
private fun EyeBreakField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    DurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.VisibilityOff,
        label = stringResource(Res.string.eye_break),
    )
}

@Composable
private fun SitBreakField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    DurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.AccessibilityNew,
        label = stringResource(Res.string.sit_break),
    )
}

@Composable
private fun FullRestField(
    duration: Duration,
    onDurationChange: (Duration) -> Unit,
) {
    DurationField(
        duration = duration,
        onDurationChange = onDurationChange,
        leadingIcon = Icons.TwoTone.EmojiFoodBeverage,
        label = stringResource(Res.string.full_rest),
    )
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
    ) {
        Icon(imageVector = Icons.TwoTone.Save, contentDescription = null)
        Spacer(width = 8.dp)
        Text(text = stringResource(Res.string.save))
    }
}
