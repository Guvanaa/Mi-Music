// Xiaomi Vela / Zepp OS Native App Entry Point for Mi Band 10
// JS Runtime for Xiaomi Smart Band 10 (192x490 AMOLED Display)

Page({
  data: {
    title: 'Midnight City',
    artist: 'M83',
    isPlaying: false,
    volume: 70,
    activeApp: 'Spotify',
    isConnected: true
  },

  onInit() {
    console.log("Mi Music Band 10 native runtime initialized.");
    this.initBluetoothSync();
  },

  initBluetoothSync() {
    // Bluetooth BLE media session client listener
    if (typeof hmBle !== 'undefined') {
      hmBle.createConnect((status) => {
        this.setData({ isConnected: status === 1 });
      });
    }
  },

  onPlayPauseToggle() {
    this.setData({ isPlaying: !this.data.isPlaying });
    if (typeof hmApp !== 'undefined') {
      hmApp.vibrate(1); // Subtle haptic buzz
    }
  },

  onNextTrack() {
    console.log("Next track requested from Mi Band 10 hardware button/gesture");
  },

  onPrevTrack() {
    console.log("Previous track requested from Mi Band 10 hardware button/gesture");
  }
});
