package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BandTab
import com.example.model.DisplayLayoutMode
import com.example.model.EQ_PRESETS
import com.example.model.EqPreset
import com.example.model.INITIAL_SONGS
import com.example.model.MUSIC_APPS
import com.example.model.MusicApp
import com.example.model.Song
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MusicUiState(
    val songs: List<Song> = INITIAL_SONGS,
    val currentSongId: String = INITIAL_SONGS[0].id,
    val isPlaying: Boolean = false,
    val currentTime: Int = 0,
    val volume: Int = 70,
    val activeAppId: String = "spotify",
    val isConnected: Boolean = true,
    val lastActionSource: String = "Smartphone",
    val currentEq: String = "bass",
    val sleepTimerMinutes: Int = 0,
    val bandTab: BandTab = BandTab.NOW_PLAYING,
    val layoutMode: DisplayLayoutMode = DisplayLayoutMode.DUAL,
    val showPhoneLyrics: Boolean = false,
    val showBandLyrics: Boolean = false,
    val showBandVolumeSlider: Boolean = false
) {
    val currentSong: Song
        get() = songs.find { it.id == currentSongId } ?: songs.first()

    val activeApp: MusicApp
        get() = MUSIC_APPS.find { it.id == activeAppId } ?: MUSIC_APPS.first()

    val currentEqPreset: EqPreset
        get() = EQ_PRESETS.find { it.id == currentEq } ?: EQ_PRESETS.first()

    val activeLyricIndex: Int
        get() {
            val lyrics = currentSong.lyrics
            if (lyrics.isEmpty()) return 0
            var activeIdx = 0
            for (i in lyrics.indices) {
                if (currentTime >= lyrics[i].time) {
                    activeIdx = i
                }
            }
            return activeIdx
        }
}

class MusicViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MusicUiState())
    val uiState: StateFlow<MusicUiState> = _uiState.asStateFlow()

    private var playbackJob: Job? = null
    private var sleepTimerJob: Job? = null

    init {
        startPlaybackTicker()
        startSleepTimerTicker()
    }

    private fun startPlaybackTicker() {
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val state = _uiState.value
                if (state.isPlaying) {
                    if (state.currentTime >= state.currentSong.duration) {
                        nextSong(state.lastActionSource)
                    } else {
                        _uiState.update { it.copy(currentTime = it.currentTime + 1) }
                    }
                }
            }
        }
    }

    private fun startSleepTimerTicker() {
        sleepTimerJob?.cancel()
        sleepTimerJob = viewModelScope.launch {
            while (true) {
                delay(60_000L)
                val state = _uiState.value
                if (state.sleepTimerMinutes > 0 && state.isPlaying) {
                    if (state.sleepTimerMinutes <= 1) {
                        _uiState.update {
                            it.copy(
                                sleepTimerMinutes = 0,
                                isPlaying = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(sleepTimerMinutes = it.sleepTimerMinutes - 1)
                        }
                    }
                }
            }
        }
    }

    fun toggleFavorite(songId: String? = null) {
        _uiState.update { state ->
            val targetId = songId ?: state.currentSongId
            val updatedSongs = state.songs.map { song ->
                if (song.id == targetId) {
                    song.copy(isFavorite = !song.isFavorite)
                } else {
                    song
                }
            }
            state.copy(songs = updatedSongs)
        }
    }

    fun playSong(songId: String, source: String = "Smartphone") {
        _uiState.update { state ->
            val targetSong = state.songs.find { it.id == songId }
            state.copy(
                currentSongId = songId,
                currentTime = 0,
                isPlaying = true,
                lastActionSource = source,
                activeAppId = targetSong?.appSource ?: state.activeAppId
            )
        }
    }

    fun togglePlayPause(source: String = "Smartphone") {
        _uiState.update { state ->
            state.copy(
                isPlaying = !state.isPlaying,
                lastActionSource = source
            )
        }
    }

    fun nextSong(source: String = "Smartphone") {
        _uiState.update { state ->
            val currentIndex = state.songs.indexOfFirst { it.id == state.currentSongId }.coerceAtLeast(0)
            val nextIndex = (currentIndex + 1) % state.songs.size
            state.copy(
                currentSongId = state.songs[nextIndex].id,
                currentTime = 0,
                lastActionSource = source
            )
        }
    }

    fun previousSong(source: String = "Smartphone") {
        _uiState.update { state ->
            val currentIndex = state.songs.indexOfFirst { it.id == state.currentSongId }.coerceAtLeast(0)
            val prevIndex = (currentIndex - 1 + state.songs.size) % state.songs.size
            state.copy(
                currentSongId = state.songs[prevIndex].id,
                currentTime = 0,
                lastActionSource = source
            )
        }
    }

    fun seekTime(time: Int, source: String = "Smartphone") {
        _uiState.update { state ->
            val clamped = time.coerceIn(0, state.currentSong.duration)
            state.copy(
                currentTime = clamped,
                lastActionSource = source
            )
        }
    }

    fun setVolumeLevel(valLevel: Int, source: String = "Smartphone") {
        _uiState.update { state ->
            state.copy(
                volume = valLevel.coerceIn(0, 100),
                lastActionSource = source
            )
        }
    }

    fun selectMusicApp(appId: String, source: String = "Smartphone") {
        _uiState.update { state ->
            state.copy(
                activeAppId = appId,
                lastActionSource = source
            )
        }
    }

    fun setEqPreset(eqId: String, source: String = "Smartphone") {
        _uiState.update { state ->
            state.copy(
                currentEq = eqId,
                lastActionSource = source
            )
        }
    }

    fun setSleepTimer(mins: Int, source: String = "Smartphone") {
        _uiState.update { state ->
            state.copy(
                sleepTimerMinutes = mins.coerceAtLeast(0),
                lastActionSource = source
            )
        }
    }

    fun toggleConnection() {
        _uiState.update { state ->
            state.copy(isConnected = !state.isConnected)
        }
    }

    fun setBandTab(tab: BandTab) {
        _uiState.update { state ->
            state.copy(
                bandTab = tab,
                showBandVolumeSlider = false
            )
        }
    }

    fun setLayoutMode(mode: DisplayLayoutMode) {
        _uiState.update { state ->
            state.copy(layoutMode = mode)
        }
    }

    fun togglePhoneLyrics() {
        _uiState.update { state ->
            state.copy(showPhoneLyrics = !state.showPhoneLyrics)
        }
    }

    fun toggleBandLyrics() {
        _uiState.update { state ->
            state.copy(showBandLyrics = !state.showBandLyrics)
        }
    }

    fun setBandVolumeSliderVisible(visible: Boolean) {
        _uiState.update { state ->
            state.copy(showBandVolumeSlider = visible)
        }
    }
}
