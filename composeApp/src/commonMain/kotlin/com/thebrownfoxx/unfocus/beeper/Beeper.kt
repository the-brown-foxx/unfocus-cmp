package com.thebrownfoxx.unfocus.beeper

import com.thebrownfoxx.unfocus.domain.Ticker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import unfocus.composeapp.generated.resources.Res
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalResourceApi::class)
class Beeper {
    private val coroutineScope = CoroutineScope(Dispatchers.Main) + SupervisorJob()

    private var clip: Clip? = null

    private val ticker = Ticker(
        tickInterval = 100.milliseconds,
        startImmediately = true,
    )

    init {
        coroutineScope.launch(Dispatchers.IO) {
            val stream = Res.readBytes("files/beep.wav").inputStream()
            val beep = AudioSystem.getAudioInputStream(stream)
            clip = AudioSystem.getClip()
            clip?.open(beep)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun beep() {
        coroutineScope.launch {
            clip?.framePosition = 0
            clip?.start()
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