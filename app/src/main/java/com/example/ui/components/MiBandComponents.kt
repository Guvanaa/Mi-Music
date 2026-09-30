package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BandTab
import com.example.model.MUSIC_APPS
import com.example.model.formatTime
import com.example.service.AudioSimulator
import com.example.ui.theme.MiOrange
import com.example.ui.theme.Purple400
import com.example.ui.theme.Purple900
import com.example.ui.theme.Rose500
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.viewmodel.MusicUiState
import kotlin.math.roundToInt

@Composable
fun MiBandFrame(
    activeTab: BandTab,
    onTabChange: (BandTab) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Handle back button when on secondary band tabs (APPS or MUSIC)
    BackHandler(enabled = activeTab != BandTab.NOW_PLAYING) {
        onTabChange(BandTab.NOW_PLAYING)
    }

    Column(
        modifier = modifier.testTag("mi_band_frame"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Strap Top
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Brush.verticalGradient(listOf(Slate900, Slate800)))
                .border(
                    width = 1.dp,
                    color = Slate700.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Slate700.copy(alpha = 0.5f))
            )
        }

        // Mi Band 10 Body / Bezel
        Box(
            modifier = Modifier
                .width(236.dp)
                .height(490.dp)
                .clip(RoundedCornerShape(52.dp))
                .background(Color.Black)
                .border(4.dp, Slate700, RoundedCornerShape(52.dp))
                .padding(10.dp)
        ) {
            // Curved AMOLED Screen Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(42.dp))
                    .background(Color.Black)
                    .border(1.dp, Slate900, RoundedCornerShape(42.dp)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Main Display View Content
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    content()
                }

                // Smartwatch Bottom Navigation Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(Color.Black.copy(alpha = 0.92f))
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BandBottomNavItem(
                        label = "PLAYER",
                        selected = activeTab == BandTab.NOW_PLAYING,
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onTabChange(BandTab.NOW_PLAYING)
                        },
                        testTag = "band_tab_nowplaying"
                    )
                    BandBottomNavItem(
                        label = "APPS",
                        selected = activeTab == BandTab.CHOOSER,
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onTabChange(BandTab.CHOOSER)
                        },
                        testTag = "band_tab_chooser"
                    )
                    BandBottomNavItem(
                        label = "MUSIC",
                        selected = activeTab == BandTab.TRACKS,
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onTabChange(BandTab.TRACKS)
                        },
                        testTag = "band_tab_tracks"
                    )
                }
            }
        }

        // Strap Bottom
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.verticalGradient(listOf(Slate800, Slate900)))
                .border(
                    width = 1.dp,
                    color = Slate700.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Slate700.copy(alpha = 0.5f))
            )
        }
    }
}

@Composable
private fun BandBottomNavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = if (selected) MiOrange else Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.2).sp
        )
        if (selected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(MiOrange)
            )
        }
    }
}

@Composable
fun BandNowPlaying(
    uiState: MusicUiState,
    onTogglePlayPause: () -> Unit,
    onPreviousSong: () -> Unit,
    onNextSong: () -> Unit,
    onSetVolume: (Int) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onToggleBandLyrics: () -> Unit,
    onSetVolumeSliderVisible: (Boolean) -> Unit,
    onOpenChooser: () -> Unit,
    onOpenTracks: () -> Unit
) {
    val currentSong = uiState.currentSong
    val activeApp = uiState.activeApp
    val progressFraction = (uiState.currentTime.toFloat() / currentSong.duration.toFloat().coerceAtLeast(1f))
        .coerceIn(0f, 1f)

    if (!uiState.isConnected) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag("band_disconnected_view"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = "Disconnected",
                tint = Slate600,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.phone_disconnected),
                color = Slate300,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.connect_phone_hint),
                color = Slate500,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Smartwatch Status Bar
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(activeApp.color)
                        )
                        Text(
                            text = activeApp.name,
                            color = Slate300,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.testTag("band_active_app_name")
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (uiState.sleepTimerMinutes > 0) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Purple900.copy(alpha = 0.65f)
                            ) {
                                Text(
                                    text = "${uiState.sleepTimerMinutes}m",
                                    color = Purple400,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "10:42",
                            color = Slate400,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                HorizontalDivider(color = Slate900)
            }

            // Album Cover Art / Synced Lyrics Display
            if (!uiState.showBandLyrics) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Brush.linearGradient(currentSong.coverGradientColors))
                            .border(1.dp, Slate800, RoundedCornerShape(18.dp))
                    ) {
                        // Center Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.12f))
                                .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getCoverIconVector(currentSong.coverIcon),
                                contentDescription = currentSong.title,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Quick Heart Favorite Toggle on Band
                        Surface(
                            onClick = {
                                AudioSimulator.playHapticClick()
                                onToggleFavorite(currentSong.id)
                            },
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(6.dp)
                                .size(24.dp)
                                .testTag("band_favorite_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (currentSong.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite on Band",
                                    tint = if (currentSong.isFavorite) Rose500 else Slate300,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        // Active play indicator pill
                        if (uiState.isPlaying) {
                            val infiniteTransition = rememberInfiniteTransition(label = "eqPulse")
                            val barScale by infiniteTransition.animateFloat(
                                initialValue = 0.5f,
                                targetValue = 1f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(450),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "barScale"
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 6.dp, vertical = 3.dp),
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height((8 * barScale).dp)
                                        .clip(CircleShape)
                                        .background(MiOrange)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height((12 * barScale).dp)
                                        .clip(CircleShape)
                                        .background(MiOrange)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height((6 * barScale).dp)
                                        .clip(CircleShape)
                                        .background(MiOrange)
                                )
                            }
                        }
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Slate950,
                    border = BorderStroke(1.dp, Slate900),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(112.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.live_wrist_lyrics).uppercase(),
                            color = MiOrange,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        val activeLine = currentSong.lyrics.getOrNull(uiState.activeLyricIndex)?.text
                            ?: "No lyrics available"
                        Text(
                            text = activeLine,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Song Metadata Title & Artist
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentSong.title,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = currentSong.artist,
                    color = Slate400,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }

            // Mini Progress Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(Slate800)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(MiOrange)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(uiState.currentTime),
                        color = Slate500,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = formatTime(currentSong.duration),
                        color = Slate500,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            // Smartwatch Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onPreviousSong()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("band_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Band Previous",
                        tint = Slate300,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onTogglePlayPause()
                    },
                    shape = CircleShape,
                    color = MiOrange,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .size(42.dp)
                        .testTag("band_play_pause_button")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (uiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (uiState.isPlaying) "Band Pause" else "Band Play",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onNextSong()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("band_next_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Band Next",
                        tint = Slate300,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Quick Controls Toolbar (Volume, Lyrics, Apps, Tracks)
            Column {
                HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Volume Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                AudioSimulator.playHapticClick()
                                onSetVolumeSliderVisible(!uiState.showBandVolumeSlider)
                            }
                            .padding(4.dp)
                            .testTag("band_volume_toggle"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.volume == 0) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Band Volume",
                            tint = if (uiState.showBandVolumeSlider) MiOrange else Slate400,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${uiState.volume}%",
                            color = if (uiState.showBandVolumeSlider) MiOrange else Slate400,
                            fontSize = 9.sp
                        )
                    }

                    // Toggle Lyrics Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                AudioSimulator.playHapticClick()
                                onToggleBandLyrics()
                            }
                            .padding(4.dp)
                            .semantics {
                                role = Role.Button
                                contentDescription = "Toggle Lyrics"
                            }
                            .testTag("band_lyrics_toggle")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Toggle Lyrics",
                            tint = MiOrange,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    // Switch App Source Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                AudioSimulator.playHapticClick()
                                onOpenChooser()
                            }
                            .padding(4.dp)
                            .semantics {
                                role = Role.Button
                                contentDescription = "Switch App Source"
                            }
                            .testTag("band_apps_button"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Switch App Source",
                            tint = MiOrange,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Apps",
                            color = Slate400,
                            fontSize = 9.sp
                        )
                    }

                    // Track Library Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                AudioSimulator.playHapticClick()
                                onOpenTracks()
                            }
                            .padding(4.dp)
                            .semantics {
                                role = Role.Button
                                contentDescription = "Track Library"
                            }
                            .testTag("band_tracks_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Track Library",
                            tint = MiOrange,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }

        // Overlay Volume Control Popup
        if (uiState.showBandVolumeSlider) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Slate900.copy(alpha = 0.96f),
                border = BorderStroke(1.dp, Slate800),
                shadowElevation = 12.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 34.dp)
                    .fillMaxWidth()
                    .testTag("band_volume_popup")
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.band_volume),
                            color = Slate300,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Volume Popup",
                            tint = Slate400,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onSetVolumeSliderVisible(false) }
                                .testTag("band_volume_close")
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeDown,
                            contentDescription = "Volume Down",
                            tint = Slate400,
                            modifier = Modifier.size(14.dp)
                        )
                        Slider(
                            value = uiState.volume.toFloat(),
                            onValueChange = { onSetVolume(it.roundToInt()) },
                            valueRange = 0f..100f,
                            colors = SliderDefaults.colors(
                                thumbColor = MiOrange,
                                activeTrackColor = MiOrange,
                                inactiveTrackColor = Slate800
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("band_volume_slider")
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Volume Up",
                            tint = Slate400,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BandAppChooser(
    activeAppId: String,
    onSelectApp: (String) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
            .testTag("band_app_chooser"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onClose()
                    },
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("band_chooser_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Player",
                        tint = Slate400,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.select_app),
                    color = Slate200,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 8.dp))

            Text(
                text = stringResource(R.string.control_smartphone_hint),
                color = Slate400,
                fontSize = 9.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                MUSIC_APPS.forEach { app ->
                    val isSelected = activeAppId == app.id
                    Surface(
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onSelectApp(app.id)
                            onClose()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Slate900 else Slate950.copy(alpha = 0.85f),
                        border = BorderStroke(1.dp, if (isSelected) MiOrange else Slate900),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("band_chooser_app_${app.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(7.dp))
                                        .background(app.color),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = app.name.first().toString(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = app.name,
                                    color = if (isSelected) Color.White else Slate300,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MiOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column {
            HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Smartphone,
                    contentDescription = null,
                    tint = Slate600,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.mi_band_sync),
                    color = Slate500,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
fun BandTrackBrowser(
    uiState: MusicUiState,
    onPlaySong: (String) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
            .testTag("band_track_browser"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onClose()
                    },
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("band_tracks_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Player",
                        tint = Slate400,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.phone_tracks),
                    color = Slate200,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                uiState.songs.forEach { song ->
                    val isSelected = uiState.currentSong.id == song.id
                    Surface(
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onPlaySong(song.id)
                            onClose()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MiOrange.copy(alpha = 0.2f) else Slate950.copy(alpha = 0.85f),
                        border = BorderStroke(1.dp, if (isSelected) MiOrange else Slate900),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("band_track_item_${song.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = song.title,
                                    color = if (isSelected) Color.White else Slate300,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = song.artist,
                                    color = Slate400,
                                    fontSize = 9.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(MiOrange)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play ${song.title}",
                                    tint = Slate600,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column {
            HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Slate600,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.select_to_play_on_phone),
                    color = Slate500,
                    fontSize = 9.sp
                )
            }
        }
    }
}
