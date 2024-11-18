package com.thebrownfoxx.unfocus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.thebrownfoxx.unfocus.ui.component.LocalWindowScope
import com.thebrownfoxx.unfocus.ui.theme.UnfocusTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.app_name
import unfocus.composeapp.generated.resources.super_u_mono_bg
import java.awt.Dimension
import java.awt.Taskbar

fun main() = application {
    val windowState = rememberWindowState()

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        undecorated = true,
        transparent = true,
        icon = painterResource(Res.drawable.super_u_mono_bg),
    ) {
        window.minimumSize = Dimension(600, 400)
        CompositionLocalProvider(LocalWindowScope provides this) {
            UnfocusTheme {
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
                    App(
                        onFlashTaskbar = { Taskbar.getTaskbar().requestWindowUserAttention(window) },
                        onMinimize = { window.isMinimized = true },
                        onClose = { window.dispose() },
                    )
                }
            }
        }
    }
}