package com.thebrownfoxx.unfocus.ui.screen.timer

import com.thebrownfoxx.unfocus.command.Command

val TimerViewModel.commands
    get() = listOf(
        Command(keyword = "announce", argumentCount = 1) {
            val announce = it.getOrNull(0) == "true"
            setAnnouncePresence(announce)
        },
        Command(keyword = "announce") { toggleAnnouncePresence() },
        Command(keyword = "skip") { skipPhase() },
        Command(keyword = "test") { enableTestMode() },
        Command(keyword = "reset") { reset() },
    )