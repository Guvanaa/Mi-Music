package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.EQ_PRESETS
import com.example.model.MUSIC_APPS
import com.example.model.formatTime
import com.example.service.AudioSimulator
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.MiOrange
import com.example.ui.theme.Purple400
import com.example.ui.theme.Rose400
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
fun SmartphonePlayer(
    uiState: MusicUiState,
    onToggleConnection: () -> Unit,
    onSelectMusicApp: (String) -> Unit,
    onSetEqPreset: (String) -> Unit,
    onSetSleepTimer: (Int) -> Unit,
    onToggleLyrics: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onSeekTime: (Int) -> Unit,
    onPreviousSong: () -> Unit,
    onTogglePlayPause: () -> Unit,
    onNextSong: () -> Unit,
    onSetVolume: (Int) -> Unit,
    onPlaySong: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSong = uiState.currentSong
    val activeApp = uiState.activeApp
    val activeLyricIndex = uiState.activeLyricIndex

    var eqDropdownExpanded by remember { mutableStateOf(false) }
    var sleepDropdownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp)
            .testTag("smartphone_player_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        border = BorderStroke(1.dp, Slate800),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Top Phone Header / Notch & Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = "Smartphone",
                        tint = MiOrange,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(R.string.xiaomi_smartphone),
                        color = Slate200,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                val connBgColor = if (uiState.isConnected) Emerald500.copy(alpha = 0.12f) else Rose500.copy(alpha = 0.12f)
                val connBorderColor = if (uiState.isConnected) Emerald500.copy(alpha = 0.35f) else Rose500.copy(alpha = 0.35f)
                val connTextColor = if (uiState.isConnected) Emerald400 else Rose400

                Surface(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onToggleConnection()
                    },
                    shape = CircleShape,
                    color = connBgColor,
                    border = BorderStroke(1.dp, connBorderColor),
                    modifier = Modifier.testTag("phone_connection_toggle")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isConnected) Icons.Default.Wifi else Icons.Default.WifiOff,
                            contentDescription = if (uiState.isConnected) "Band Connected" else "Disconnected",
                            tint = connTextColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (uiState.isConnected) stringResource(R.string.band_connected) else stringResource(R.string.disconnected),
                            color = connTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            HorizontalDivider(color = Slate800, modifier = Modifier.padding(bottom = 14.dp))

            // Sync Indicator Banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Slate800.copy(alpha = 0.8f),
                border = BorderStroke(1.dp, Slate700.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
                    .testTag("sync_indicator_banner")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Watch,
                            contentDescription = "Mi Band 10 Mirroring",
                            tint = MiOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.mi_band_mirroring),
                            color = Slate300,
                            fontSize = 12.sp
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Slate700,
                        modifier = Modifier.testTag("sync_indicator_badge")
                    ) {
                        Text(
                            text = "Active (${uiState.lastActionSource})",
                            color = Slate200,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Music App Source Selector
            Text(
                text = stringResource(R.string.music_service_source).uppercase(),
                color = Slate400,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 4-column grid of music apps
            val appRows = MUSIC_APPS.chunked(4)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                appRows.forEach { rowApps ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowApps.forEach { app ->
                            val isSelected = activeApp.id == app.id
                            val borderColor by animateColorAsState(
                                targetValue = if (isSelected) MiOrange else Slate800,
                                label = "appBorder"
                            )
                            val cardBg by animateColorAsState(
                                targetValue = if (isSelected) Slate800 else Slate950.copy(alpha = 0.5f),
                                label = "appBg"
                            )

                            Surface(
                                onClick = {
                                    AudioSimulator.playHapticClick()
                                    onSelectMusicApp(app.id)
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = cardBg,
                                border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, borderColor),
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        role = Role.Button
                                        contentDescription = app.name
                                    }
                                    .testTag("phone_app_${app.id}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(app.color),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = app.name.first().toString(),
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = app.name,
                                        color = if (isSelected) Color.White else Slate400,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        // Fill remaining columns in the last row
                        repeat(4 - rowApps.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // Convenient Toolbar (EQ, Sleep Timer, Lyrics Toggle)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Slate950.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, Slate800),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // EQ Preset Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Equalizer,
                                contentDescription = "EQ Preset",
                                tint = MiOrange,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = stringResource(R.string.eq_preset).uppercase(),
                                color = Slate500,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box {
                            Surface(
                                onClick = { eqDropdownExpanded = true },
                                shape = RoundedCornerShape(6.dp),
                                color = Slate900,
                                border = BorderStroke(1.dp, Slate700),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics {
                                        role = Role.DropdownList
                                        contentDescription = "EQ Preset"
                                    }
                                    .testTag("eq_preset_selector")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = uiState.currentEqPreset.name,
                                        color = Slate200,
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Expand EQ options",
                                        tint = Slate400,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = eqDropdownExpanded,
                                onDismissRequest = { eqDropdownExpanded = false },
                                modifier = Modifier.background(Slate900)
                            ) {
                                EQ_PRESETS.forEach { preset ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = "${preset.name} (${preset.bass}/${preset.mid}/${preset.treble})",
                                                color = if (preset.id == uiState.currentEq) MiOrange else Slate200,
                                                fontSize = 12.sp
                                            )
                                        },
                                        onClick = {
                                            AudioSimulator.playHapticClick()
                                            onSetEqPreset(preset.id)
                                            eqDropdownExpanded = false
                                        },
                                        modifier = Modifier.testTag("eq_option_${preset.id}")
                                    )
                                }
                            }
                        }
                    }

                    // Sleep Timer Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bedtime,
                                contentDescription = "Sleep Timer",
                                tint = Purple400,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = stringResource(R.string.sleep_timer).uppercase(),
                                color = Slate500,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box {
                            val sleepLabel = if (uiState.sleepTimerMinutes == 0) "Off" else "${uiState.sleepTimerMinutes} Min"
                            Surface(
                                onClick = { sleepDropdownExpanded = true },
                                shape = RoundedCornerShape(6.dp),
                                color = Slate900,
                                border = BorderStroke(1.dp, Slate700),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .semantics {
                                        role = Role.DropdownList
                                        contentDescription = "Sleep Timer"
                                    }
                                    .testTag("sleep_timer_selector")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = sleepLabel,
                                        color = Slate200,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Expand Sleep Timer options",
                                        tint = Slate400,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = sleepDropdownExpanded,
                                onDismissRequest = { sleepDropdownExpanded = false },
                                modifier = Modifier.background(Slate900)
                            ) {
                                listOf(0 to "Off", 15 to "15 Min", 30 to "30 Min", 60 to "60 Min").forEach { (mins, label) ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = label,
                                                color = if (mins == uiState.sleepTimerMinutes) MiOrange else Slate200,
                                                fontSize = 12.sp
                                            )
                                        },
                                        onClick = {
                                            AudioSimulator.playHapticClick()
                                            onSetSleepTimer(mins)
                                            sleepDropdownExpanded = false
                                        },
                                        modifier = Modifier.testTag("sleep_option_$mins")
                                    )
                                }
                            }
                        }
                    }

                    // Synced Lyrics Toggle
                    Surface(
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onToggleLyrics()
                        },
                        shape = RoundedCornerShape(6.dp),
                        color = if (uiState.showPhoneLyrics) MiOrange.copy(alpha = 0.2f) else Slate900,
                        border = BorderStroke(1.dp, if (uiState.showPhoneLyrics) MiOrange else Slate800),
                        modifier = Modifier
                            .weight(0.9f)
                            .testTag("phone_lyrics_toggle")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "Lyrics",
                                tint = if (uiState.showPhoneLyrics) MiOrange else Slate400,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.lyrics),
                                color = if (uiState.showPhoneLyrics) MiOrange else Slate400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Album Artwork or Synced Lyrics Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.15f)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Slate800, RoundedCornerShape(16.dp))
                    .testTag("phone_artwork_or_lyrics_box")
            ) {
                if (!uiState.showPhoneLyrics) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.linearGradient(currentSong.coverGradientColors))
                            .padding(20.dp)
                    ) {
                        // Active App Source Badge Top Right
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = activeApp.color,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .testTag("phone_active_app_badge")
                        ) {
                            Text(
                                text = activeApp.name,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        // Center Cover Icon + Album + Title + Artist
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.12f))
                                    .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getCoverIconVector(currentSong.coverIcon),
                                    contentDescription = currentSong.title,
                                    tint = Color.White.copy(alpha = 0.92f),
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = currentSong.album.uppercase(),
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.2.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = currentSong.title,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(
                                    onClick = {
                                        AudioSimulator.playHapticClick()
                                        onToggleFavorite(currentSong.id)
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .testTag("phone_favorite_button")
                                ) {
                                    Icon(
                                        imageVector = if (currentSong.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Toggle Favorite",
                                        tint = if (currentSong.isFavorite) Rose500 else Slate300,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Text(
                                text = currentSong.artist,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Slate950)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.synced_live_lyrics).uppercase(),
                            color = MiOrange,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        if (currentSong.lyrics.isNotEmpty()) {
                            currentSong.lyrics.forEachIndexed { idx, line ->
                                val isActive = idx == activeLyricIndex
                                Text(
                                    text = line.text,
                                    color = if (isActive) MiOrange else Slate600,
                                    fontSize = if (isActive) 14.sp else 12.sp,
                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            AudioSimulator.playHapticClick()
                                            onSeekTime(line.time)
                                        }
                                        .padding(vertical = 6.dp, horizontal = 8.dp)
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.no_lyrics_available),
                                color = Slate500,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Song Info & Scrubber
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(uiState.currentTime),
                    color = Slate400,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = formatTime(currentSong.duration),
                    color = Slate400,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Slider(
                value = uiState.currentTime.toFloat(),
                onValueChange = { newVal ->
                    onSeekTime(newVal.roundToInt())
                },
                valueRange = 0f..currentSong.duration.toFloat().coerceAtLeast(1f),
                colors = SliderDefaults.colors(
                    thumbColor = MiOrange,
                    activeTrackColor = MiOrange,
                    inactiveTrackColor = Slate800
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("phone_seek_slider")
            )

            // Player Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onPreviousSong()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .testTag("phone_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous Track",
                        tint = Slate300,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Surface(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onTogglePlayPause()
                    },
                    shape = CircleShape,
                    color = MiOrange,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .size(60.dp)
                        .testTag("phone_play_pause_button")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (uiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (uiState.isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                IconButton(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onNextSong()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .testTag("phone_next_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next Track",
                        tint = Slate300,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Volume Control
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Slate950.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, Slate800),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (uiState.volume == 0) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Volume",
                        tint = Slate400,
                        modifier = Modifier.size(18.dp)
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
                            .testTag("phone_volume_slider")
                    )
                    Text(
                        text = "${uiState.volume}%",
                        color = Slate400,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.width(34.dp),
                        textAlign = TextAlign.End
                    )
                }
            }

            // Playlist Preview
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.smartphone_music_library).uppercase(),
                    color = Slate400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Library",
                    tint = Slate500,
                    modifier = Modifier.size(15.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.songs.forEach { song ->
                    val isSelected = song.id == currentSong.id
                    Surface(
                        onClick = {
                            AudioSimulator.playHapticClick()
                            onPlaySong(song.id)
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MiOrange.copy(alpha = 0.15f) else Slate950.copy(alpha = 0.45f),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) MiOrange.copy(alpha = 0.45f) else Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("phone_track_${song.id}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(
                                        text = song.title,
                                        color = if (isSelected) Color.White else Slate300,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    if (song.isFavorite) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Favorite",
                                            tint = Rose500,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = song.artist,
                                    color = Slate400,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Currently Selected",
                                    tint = MiOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
