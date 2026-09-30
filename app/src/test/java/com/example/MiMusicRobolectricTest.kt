package com.example

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.ui.theme.MiMusicTheme
import com.example.viewmodel.MusicViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class MiMusicRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rendersApplicationHeaderAndDualDevicePanels() {
        val viewModel = MusicViewModel()
        composeTestRule.setContent {
            MiMusicTheme {
                MiMusicAppScreen(uiState = viewModel.uiState.value, viewModel = viewModel)
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Xiaomi Smartphone").assertIsDisplayed()
        composeTestRule.onNodeWithText("Xiaomi Mi Band 10 (\"Mi Music\")").assertExists()
    }

    @Test
    fun displaysInitialSongMetadataOnBothSmartphoneAndSmartwatch() {
        val viewModel = MusicViewModel()
        composeTestRule.setContent {
            MiMusicTheme {
                MiMusicAppScreen(uiState = viewModel.uiState.value, viewModel = viewModel)
            }
        }
        composeTestRule.waitForIdle()

        val midnightCityNodes = composeTestRule.onAllNodesWithText("Midnight City").fetchSemanticsNodes()
        assertTrue(midnightCityNodes.size >= 2)

        val m83Nodes = composeTestRule.onAllNodesWithText("M83").fetchSemanticsNodes()
        assertTrue(m83Nodes.size >= 2)
    }

    @Test
    fun switchesAppMusicSourcesAndSupportsMetrolist() {
        val viewModel = MusicViewModel()
        viewModel.selectMusicApp("apple", "Smartphone")
        assertEquals("apple", viewModel.uiState.value.activeAppId)
        assertEquals("Apple Music", viewModel.uiState.value.activeApp.name)

        viewModel.selectMusicApp("metrolist", "Mi Band 10")
        assertEquals("metrolist", viewModel.uiState.value.activeAppId)
        assertEquals("Metrolist", viewModel.uiState.value.activeApp.name)
        assertEquals("Mi Band 10", viewModel.uiState.value.lastActionSource)
    }

    @Test
    fun supportsEqPresetsSleepTimerAndTrackNavigation() {
        val viewModel = MusicViewModel()
        viewModel.setEqPreset("vocal", "Smartphone")
        assertEquals("vocal", viewModel.uiState.value.currentEq)
        assertEquals("Vocal", viewModel.uiState.value.currentEqPreset.name)

        viewModel.setSleepTimer(30, "Smartphone")
        assertEquals(30, viewModel.uiState.value.sleepTimerMinutes)

        viewModel.nextSong("Mi Band 10")
        assertEquals("song-2", viewModel.uiState.value.currentSongId)
        assertEquals("Blinding Lights", viewModel.uiState.value.currentSong.title)
        assertEquals("Mi Band 10", viewModel.uiState.value.lastActionSource)
    }
}
