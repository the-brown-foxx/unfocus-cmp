package com.thebrownfoxx.unfocus.domain

enum class Phase {
    Focus,
    EyeBreak,
    SitBreak,
    FullRest;

    companion object {
        val queue = listOf(
            Focus,
            EyeBreak,
            Focus,
            EyeBreak,
            Focus,
            SitBreak,
            Focus,
            EyeBreak,
            Focus,
            EyeBreak,
            Focus,
            FullRest,
        )
    }
}