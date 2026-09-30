package com.example.model

import androidx.compose.ui.graphics.Color

data class LyricLine(
    val time: Int,
    val text: String
)

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val appSource: String,
    val coverGradientColors: List<Color>,
    val coverIcon: String,
    val accentColor: Color,
    val isFavorite: Boolean,
    val lyrics: List<LyricLine>
)

data class MusicApp(
    val id: String,
    val name: String,
    val color: Color
)

data class EqPreset(
    val id: String,
    val name: String,
    val bass: Int,
    val mid: Int,
    val treble: Int
)

enum class BandTab {
    NOW_PLAYING,
    CHOOSER,
    TRACKS
}

enum class DisplayLayoutMode(val label: String) {
    DUAL("Dual Mirror"),
    SMARTPHONE_ONLY("Phone Only"),
    BAND_ONLY("Mi Band 10")
}

val INITIAL_SONGS = listOf(
    Song(
        id = "song-1",
        title = "Midnight City",
        artist = "M83",
        album = "Hurry Up, We're Dreaming",
        duration = 243,
        appSource = "spotify",
        coverGradientColors = listOf(
            Color(0xFF581C87),
            Color(0xFF312E81),
            Color(0xFF000000)
        ),
        coverIcon = "Sparkles",
        accentColor = Color(0xFF8B5CF6),
        isFavorite = true,
        lyrics = listOf(
            LyricLine(0, "Waiting in a car..."),
            LyricLine(12, "Waiting for a ride in the dark..."),
            LyricLine(24, "The night city is an execution grid..."),
            LyricLine(38, "The city is my church, it keeps me sane..."),
            LyricLine(55, "Until the light takes us..."),
            LyricLine(80, "Midnight city, burning bright!")
        )
    ),
    Song(
        id = "song-2",
        title = "Blinding Lights",
        artist = "The Weeknd",
        album = "After Hours",
        duration = 200,
        appSource = "spotify",
        coverGradientColors = listOf(
            Color(0xFF7F1D1D),
            Color(0xFF4C0519),
            Color(0xFF000000)
        ),
        coverIcon = "Zap",
        accentColor = Color(0xFFEF4444),
        isFavorite = false,
        lyrics = listOf(
            LyricLine(0, "Yeah..."),
            LyricLine(10, "I've been tryin' to call..."),
            LyricLine(22, "I'm on my own now..."),
            LyricLine(35, "I said, ooh, I'm blinded by the lights!")
        )
    ),
    Song(
        id = "song-3",
        title = "As It Was",
        artist = "Harry Styles",
        album = "Harry's House",
        duration = 167,
        appSource = "apple",
        coverGradientColors = listOf(
            Color(0xFF1E3A8A),
            Color(0xFF082F49),
            Color(0xFF000000)
        ),
        coverIcon = "Sun",
        accentColor = Color(0xFF3B82F6),
        isFavorite = true,
        lyrics = listOf(
            LyricLine(0, "Come on, Harry, we wanna say goodnight to you!"),
            LyricLine(12, "Holdin' me back..."),
            LyricLine(28, "You know it's not the same as it was!")
        )
    ),
    Song(
        id = "song-4",
        title = "Levitating",
        artist = "Dua Lipa",
        album = "Future Nostalgia",
        duration = 203,
        appSource = "metrolist",
        coverGradientColors = listOf(
            Color(0xFF831843),
            Color(0xFF4A044E),
            Color(0xFF000000)
        ),
        coverIcon = "Disc",
        accentColor = Color(0xFFEC4899),
        isFavorite = false,
        lyrics = listOf(
            LyricLine(0, "If you wanna run away with me, I know a galaxy..."),
            LyricLine(15, "I got you, moonlight, you're my starlight..."),
            LyricLine(30, "I'm levitating!")
        )
    ),
    Song(
        id = "song-5",
        title = "Starboy",
        artist = "The Weeknd ft. Daft Punk",
        album = "Starboy",
        duration = 230,
        appSource = "spotify",
        coverGradientColors = listOf(
            Color(0xFF78350F),
            Color(0xFF431407),
            Color(0xFF000000)
        ),
        coverIcon = "Flame",
        accentColor = Color(0xFFF59E0B),
        isFavorite = true,
        lyrics = listOf(
            LyricLine(0, "I'm tryna put you in the worst mood..."),
            LyricLine(18, "Look what you've done, I'm a motherfuckin' starboy!")
        )
    ),
    Song(
        id = "song-6",
        title = "Get Lucky",
        artist = "Daft Punk",
        album = "Random Access Memories",
        duration = 248,
        appSource = "apple",
        coverGradientColors = listOf(
            Color(0xFF713F12),
            Color(0xFF451A03),
            Color(0xFF000000)
        ),
        coverIcon = "Radio",
        accentColor = Color(0xFFEAB308),
        isFavorite = false,
        lyrics = listOf(
            LyricLine(0, "Like the legend of the phoenix..."),
            LyricLine(20, "We've come too far to give up who we are..."),
            LyricLine(40, "We're up all night to get lucky!")
        )
    )
)

val MUSIC_APPS = listOf(
    MusicApp(id = "spotify", name = "Spotify", color = Color(0xFF1DB954)),
    MusicApp(id = "apple", name = "Apple Music", color = Color(0xFFFA233B)),
    MusicApp(id = "ytmusic", name = "YouTube Music", color = Color(0xFFFF0000)),
    MusicApp(id = "metrolist", name = "Metrolist", color = Color(0xFFE11D48)),
    MusicApp(id = "innertune", name = "InnerTune", color = Color(0xFF3B82F6)),
    MusicApp(id = "vimusic", name = "ViMusic", color = Color(0xFF8B5CF6)),
    MusicApp(id = "mi", name = "Mi Player", color = Color(0xFFFF6700))
)

val EQ_PRESETS = listOf(
    EqPreset(id = "flat", name = "Flat", bass = 0, mid = 0, treble = 0),
    EqPreset(id = "bass", name = "Bass Boost", bass = 8, mid = 2, treble = 1),
    EqPreset(id = "pop", name = "Pop", bass = 3, mid = 6, treble = 4),
    EqPreset(id = "vocal", name = "Vocal", bass = 1, mid = 8, treble = 5)
)

fun formatTime(seconds: Int): String {
    val safeSeconds = seconds.coerceAtLeast(0)
    val mins = safeSeconds / 60
    val secs = safeSeconds % 60
    return "$mins:${if (secs < 10) "0" else ""}$secs"
}
