package com.thebrownfoxx.unfocus.ui.screen.timer.state

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import unfocus.composeapp.generated.resources.Res
import unfocus.composeapp.generated.resources.eye_break_expired
import unfocus.composeapp.generated.resources.eye_break_instruction
import unfocus.composeapp.generated.resources.eye_break_paused
import unfocus.composeapp.generated.resources.eye_break_timer
import unfocus.composeapp.generated.resources.focus_expired
import unfocus.composeapp.generated.resources.focus_instruction
import unfocus.composeapp.generated.resources.focus_paused
import unfocus.composeapp.generated.resources.focus_timer
import unfocus.composeapp.generated.resources.full_rest_expired
import unfocus.composeapp.generated.resources.full_rest_instruction
import unfocus.composeapp.generated.resources.full_rest_paused
import unfocus.composeapp.generated.resources.full_rest_timer
import unfocus.composeapp.generated.resources.intro
import unfocus.composeapp.generated.resources.sit_break_expired
import unfocus.composeapp.generated.resources.sit_break_instruction
import unfocus.composeapp.generated.resources.sit_break_paused
import unfocus.composeapp.generated.resources.sit_break_timer

val TimerHeader.text @Composable get() = stringResource(stringResource)

private val TimerHeader.stringResource: StringResource
    get() {
        val header = this
        return with(Res.string) {
            when (header) {
                TimerHeader.Intro -> intro
                TimerHeader.FocusPaused -> focus_paused
                TimerHeader.FocusInstruction -> focus_instruction
                TimerHeader.FocusTimer -> focus_timer
                TimerHeader.FocusExpired -> focus_expired
                TimerHeader.EyeBreakPaused -> eye_break_paused
                TimerHeader.EyeBreakInstruction -> eye_break_instruction
                TimerHeader.EyeBreakTimer -> eye_break_timer
                TimerHeader.EyeBreakExpired -> eye_break_expired
                TimerHeader.SitBreakPaused -> sit_break_paused
                TimerHeader.SitBreakInstruction -> sit_break_instruction
                TimerHeader.SitBreakTimer -> sit_break_timer
                TimerHeader.SitBreakExpired -> sit_break_expired
                TimerHeader.FullRestPaused -> full_rest_paused
                TimerHeader.FullRestInstruction -> full_rest_instruction
                TimerHeader.FullRestTimer -> full_rest_timer
                TimerHeader.FullRestExpired -> full_rest_expired
            }
        }
    }