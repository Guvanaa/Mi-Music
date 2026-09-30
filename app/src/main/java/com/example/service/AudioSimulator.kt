package com.example.service

import android.media.AudioManager
import android.media.ToneGenerator

/**
 * Android Audio & Haptic Feedback Simulator corresponding to src/services/audioSimulator.js.
 * Generates short synthesized clicks for button presses on both Smartphone and Mi Band 10 UI.
 */
object AudioSimulator {
    private var toneGenerator: ToneGenerator? = null

    fun playBeep(durationMs: Int = 35) {
        try {
            if (toneGenerator == null) {
                toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 25)
            }
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, durationMs)
        } catch (_: Throwable) {
            // Safe fallback for unit test / restricted audio environments
        }
    }

    fun playHapticClick() {
        playBeep(25)
    }
}
