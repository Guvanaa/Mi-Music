// Xiaomi Vela / Zepp OS Native Watch App App Runtime
App({
  globalData: {
    appName: 'Mi Music',
    version: '1.0.0',
    target: 'Xiaomi Mi Band 10'
  },
  onCreate() {
    console.log('Mi Music Xiaomi Vela App Created');
  },
  onDestroy() {
    console.log('Mi Music Xiaomi Vela App Destroyed');
  }
});
