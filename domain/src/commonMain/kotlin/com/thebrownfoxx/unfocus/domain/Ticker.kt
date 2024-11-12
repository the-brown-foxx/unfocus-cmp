package com.thebrownfoxx.unfocus.domain

import kotlinx.datetime.Clock
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.time.Duration

class Ticker(private val tickInterval: Duration, private val startImmediately: Boolean) {
    private var javaTimer: Timer? = null

    fun run(onTick: (elapsed: Duration) -> Unit) {
        cancel()
        var lastTick = Clock.System.now()

        val tickIntervalMilliseconds = tickInterval.inWholeMilliseconds

        javaTimer = timer(
            initialDelay = if (startImmediately) 0L else tickIntervalMilliseconds,
            period = tickIntervalMilliseconds,
        ) {
            val newTick = Clock.System.now()
            onTick(newTick - lastTick)
            lastTick = newTick
        }
    }

    fun cancel() {
        javaTimer?.cancel()
        javaTimer?.purge()
    }
}