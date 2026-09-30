package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.BandTab
import com.example.model.DisplayLayoutMode
import com.example.service.AudioSimulator
import com.example.ui.components.BandAppChooser
import com.example.ui.components.BandNowPlaying
import com.example.ui.components.BandTrackBrowser
import com.example.ui.components.MiBandFrame
import com.example.ui.components.SmartphonePlayer
import com.example.ui.theme.Emerald500
import com.example.ui.theme.MiMusicTheme
import com.example.ui.theme.MiOrange
import com.example.ui.theme.Rose500
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.viewmodel.MusicUiState
import com.example.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiMusicTheme {
                val musicViewModel: MusicViewModel = viewModel()
                val uiState by musicViewModel.uiState.collectAsStateWithLifecycle()
                MiMusicAppScreen(
                    uiState = uiState,
                    viewModel = musicViewModel
                )
            }
        }
    }
}

@Composable
fun MiMusicAppScreen(
    uiState: MusicUiState,
    viewModel: MusicViewModel
) {
    BackHandler(enabled = uiState.layoutMode != DisplayLayoutMode.DUAL) {
        viewModel.setLayoutMode(DisplayLayoutMode.DUAL)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Slate950,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isExpandedScreen = maxWidth >= 760.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Navigation / Brand Banner
                HeaderBanner(
                    uiState = uiState,
                    onLayoutModeSelected = { viewModel.setLayoutMode(it) }
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Main Dual Device View (side-by-side on wide screens, stacked/selectable on compact screens)
                if (isExpandedScreen && uiState.layoutMode == DisplayLayoutMode.DUAL) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 1100.dp),
                        horizontalArrangement = Arrangement.spacedBy(28.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SmartphoneSection(uiState = uiState, viewModel = viewModel)
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MiBandSection(uiState = uiState, viewModel = viewModel)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        if (uiState.layoutMode == DisplayLayoutMode.DUAL || uiState.layoutMode == DisplayLayoutMode.SMARTPHONE_ONLY) {
                            SmartphoneSection(uiState = uiState, viewModel = viewModel)
                        }
                        if (uiState.layoutMode == DisplayLayoutMode.DUAL || uiState.layoutMode == DisplayLayoutMode.BAND_ONLY) {
                            MiBandSection(uiState = uiState, viewModel = viewModel)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Footer Info
                FooterBanner()
            }
        }
    }
}

@Composable
private fun HeaderBanner(
    uiState: MusicUiState,
    onLayoutModeSelected: (DisplayLayoutMode) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 1100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MiOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Mi Music Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = CircleShape,
                            color = MiOrange.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, MiOrange.copy(alpha = 0.35f))
                        ) {
                            Text(
                                text = stringResource(R.string.band_badge),
                                color = MiOrange,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.app_subtitle),
                        color = Slate400,
                        fontSize = 12.sp
                    )
                }
            }

            // Real-time Bluetooth Sync pill
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Slate900,
                border = BorderStroke(1.dp, Slate800)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (uiState.isConnected) Emerald500 else Rose500)
                    )
                    Text(
                        text = stringResource(R.string.realtime_bt_sync),
                        color = Slate400,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Display Mode Switcher for Handheld Ergonomics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DisplayLayoutMode.entries.forEach { mode ->
                val selected = uiState.layoutMode == mode
                Surface(
                    onClick = {
                        AudioSimulator.playHapticClick()
                        onLayoutModeSelected(mode)
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selected) MiOrange.copy(alpha = 0.18f) else Slate900,
                    border = BorderStroke(1.dp, if (selected) MiOrange else Slate800),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("layout_mode_${mode.name.lowercase()}")
                ) {
                    Text(
                        text = mode.label,
                        color = if (selected) MiOrange else Slate400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Slate800)
    }
}

@Composable
private fun SmartphoneSection(
    uiState: MusicUiState,
    viewModel: MusicViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Radio,
                contentDescription = null,
                tint = MiOrange,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(R.string.smartphone_music_source),
                color = Slate300,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        SmartphonePlayer(
            uiState = uiState,
            onToggleConnection = { viewModel.toggleConnection() },
            onSelectMusicApp = { appId -> viewModel.selectMusicApp(appId, "Smartphone") },
            onSetEqPreset = { eqId -> viewModel.setEqPreset(eqId, "Smartphone") },
            onSetSleepTimer = { mins -> viewModel.setSleepTimer(mins, "Smartphone") },
            onToggleLyrics = { viewModel.togglePhoneLyrics() },
            onToggleFavorite = { songId -> viewModel.toggleFavorite(songId) },
            onSeekTime = { time -> viewModel.seekTime(time, "Smartphone") },
            onPreviousSong = { viewModel.previousSong("Smartphone") },
            onTogglePlayPause = { viewModel.togglePlayPause("Smartphone") },
            onNextSong = { viewModel.nextSong("Smartphone") },
            onSetVolume = { vol -> viewModel.setVolumeLevel(vol, "Smartphone") },
            onPlaySong = { songId -> viewModel.playSong(songId, "Smartphone") }
        )
    }
}

@Composable
private fun MiBandSection(
    uiState: MusicUiState,
    viewModel: MusicViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = MiOrange,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(R.string.xiaomi_mi_band_header),
                color = Slate300,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        MiBandFrame(
            activeTab = uiState.bandTab,
            onTabChange = { tab -> viewModel.setBandTab(tab) }
        ) {
            when (uiState.bandTab) {
                BandTab.NOW_PLAYING -> BandNowPlaying(
                    uiState = uiState,
                    onTogglePlayPause = { viewModel.togglePlayPause("Mi Band 10") },
                    onPreviousSong = { viewModel.previousSong("Mi Band 10") },
                    onNextSong = { viewModel.nextSong("Mi Band 10") },
                    onSetVolume = { vol -> viewModel.setVolumeLevel(vol, "Mi Band 10") },
                    onToggleFavorite = { songId -> viewModel.toggleFavorite(songId) },
                    onToggleBandLyrics = { viewModel.toggleBandLyrics() },
                    onSetVolumeSliderVisible = { visible -> viewModel.setBandVolumeSliderVisible(visible) },
                    onOpenChooser = { viewModel.setBandTab(BandTab.CHOOSER) },
                    onOpenTracks = { viewModel.setBandTab(BandTab.TRACKS) }
                )

                BandTab.CHOOSER -> BandAppChooser(
                    activeAppId = uiState.activeAppId,
                    onSelectApp = { appId -> viewModel.selectMusicApp(appId, "Mi Band 10") },
                    onClose = { viewModel.setBandTab(BandTab.NOW_PLAYING) }
                )

                BandTab.TRACKS -> BandTrackBrowser(
                    uiState = uiState,
                    onPlaySong = { songId -> viewModel.playSong(songId, "Mi Band 10") },
                    onClose = { viewModel.setBandTab(BandTab.NOW_PLAYING) }
                )
            }
        }
    }
}

@Composable
private fun FooterBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 1100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(color = Slate900, modifier = Modifier.padding(bottom = 14.dp))
        Text(
            text = stringResource(R.string.footer_copyright),
            color = Slate500,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MiOrange,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.footer_info),
                color = Slate400,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
