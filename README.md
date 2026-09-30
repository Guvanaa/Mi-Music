# Mi Music — Android Companion & Xiaomi Mi Band 10 Mirror

A native Android application built with Kotlin and Jetpack Compose for Xiaomi Mi Band 10 that mirrors now-playing music from your smartphone with cover art, synced live lyrics, EQ presets, sleep timer, and multi-app source switching.

## Core Features
- **Dual-Device Real-Time Mirroring**: Seamless state synchronization between the **Xiaomi Smartphone Music Source** and the **Xiaomi Mi Band 10 ("Mi Music")** AMOLED display mirror, tracking the last action source (`Smartphone` or `Mi Band 10`).
- **Multi-Service App Source Switching**: Switch active playback sources across **Spotify**, **Apple Music**, **YouTube Music**, **Metrolist**, **InnerTune**, **ViMusic**, and **Mi Player** from either the phone or wrist app chooser.
- **Synced Live Lyrics**: Real-time time-synced lyrics on both the smartphone player and Mi Band 10 wrist display, with tap-to-seek support on lyric lines.
- **Audio & Playback Controls**: Play/pause, skip forward/backward, interactive seek bar, volume slider (0–100%) with wrist volume popup, favorite track toggling, **EQ Presets** (`Flat`, `Bass Boost`, `Pop`, `Vocal`), and **Sleep Timer** (`Off`, `15 Min`, `30 Min`, `60 Min`).
- **Bluetooth Connection State**: Toggle Bluetooth connection between the smartphone and Mi Band 10 to test connected and disconnected watch states.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (`MusicViewModel` + `StateFlow`)
- **Build System**: Gradle (Kotlin DSL)
