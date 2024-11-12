package com.thebrownfoxx.unfocus.beeper

import com.thebrownfoxx.unfocus.domain.Ticker
import korlibs.audio.sound.Sound
import korlibs.audio.sound.readMusic
import korlibs.time.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import unfocus.composeapp.generated.resources.Res
import kotlin.time.Duration

@OptIn(ExperimentalResourceApi::class)
class Beeper {
    private val coroutineScope = CoroutineScope(Dispatchers.Main) + SupervisorJob()

    private var beep: Sound? = null

    private val ticker = Ticker(
        tickInterval = 100.milliseconds,
        startImmediately = true,
    )

    init {
        coroutineScope.launch(Dispatchers.IO) {
            beep = Res.readBytes("files/beep.mp3").readMusic()
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun beep() {
        coroutineScope.launch {
            beep?.play()
        }
    }

    fun beepEvery(interval: Duration): PeriodicBeeper {
        var lastBeep = Clock.System.now()
        beep()
        ticker.run {
            val newBeep = Clock.System.now()
            if (newBeep - lastBeep >= interval) {
                beep()
                lastBeep = newBeep
            }
        }
        return PeriodicBeeper { ticker.cancel() }
    }
}