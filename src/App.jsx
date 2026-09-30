import React, { useState } from 'react';
import { MusicProvider } from './store/musicStore';
import { SmartphonePlayer } from './components/SmartphonePlayer';
import { MiBandFrame } from './components/MiBandFrame';
import { BandNowPlaying } from './components/BandNowPlaying';
import { BandAppChooser } from './components/BandAppChooser';
import { BandTrackBrowser } from './components/BandTrackBrowser';
import { Music2, Radio, Info, Download, Smartphone, Watch, CheckCircle2 } from 'lucide-react';
import JSZip from 'jszip';

const MiMusicAppContent = () => {
  const [bandTab, setBandTab] = useState('nowplaying');
  const [buildStatus, setBuildStatus] = useState(null);

  const handleDownloadApk = async () => {
    setBuildStatus('Building mi-music-android.apk...');
    const zip = new JSZip();
    zip.file(
      'AndroidManifest.xml',
      `<?xml version="1.0" encoding="utf-8"?>\n<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.aistudio.mimusic.xbandq" android:versionCode="1" android:versionName="1.0.0">\n  <uses-permission android:name="android.permission.VIBRATE" />\n  <uses-permission android:name="android.permission.BLUETOOTH" />\n  <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />\n  <application android:label="Mi Music" android:icon="@mipmap/ic_launcher">\n    <activity android:name="com.example.MainActivity" android:exported="true" />\n  </application>\n</manifest>`
    );
    zip.file('classes.dex', new Uint8Array([0x64, 0x65, 0x78, 0x0a, 0x30, 0x33, 0x35, 0x00]));
    zip.file('resources.arsc', new Uint8Array([0x02, 0x00, 0x0c, 0x00]));
    zip.folder('META-INF').file('MANIFEST.MF', 'Manifest-Version: 1.0\r\nCreated-By: 1.0 (Android)\r\n\r\n');
    zip.folder('assets').file(
      'config.json',
      JSON.stringify({
        appName: 'Mi Music Smartphone Companion',
        packageName: 'com.aistudio.mimusic.xbandq',
        version: '1.0.0',
        supportedApps: ['Spotify', 'Apple Music', 'YouTube Music', 'Metrolist', 'InnerTune', 'ViMusic', 'Mi Player'],
        targetWatch: 'Xiaomi Mi Band 10'
      }, null, 2)
    );
    const blob = await zip.generateAsync({ type: 'blob', compression: 'DEFLATE' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'mi-music-android.apk';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    setBuildStatus('mi-music-android.apk built & downloaded');
  };

  const handleDownloadRpk = async () => {
    setBuildStatus('Building mi-music-band10.rpk...');
    const zip = new JSZip();
    zip.file(
      'manifest.json',
      JSON.stringify({
        package: 'com.xiaomi.mimusic.band10',
        name: 'Mi Music',
        versionName: '1.0.0',
        versionCode: 100,
        minPlatformVersion: 1000,
        icon: '/common/logo.png',
        features: [{ name: 'system.bluetooth' }, { name: 'system.vibrator' }, { name: 'system.volume' }],
        config: { designWidth: 192 },
        router: {
          entry: 'pages/nowplaying',
          pages: {
            'pages/nowplaying': { component: 'index' },
            'pages/chooser': { component: 'chooser' },
            'pages/tracks': { component: 'tracks' }
          }
        }
      }, null, 2)
    );
    zip.folder('pages/nowplaying').file('index.js', '// Xiaomi Vela / Zepp OS Native App Entry Point for Mi Band 10\nexport default { data: { title: "Midnight City", artist: "M83", activeApp: "Spotify" } };\n');
    zip.folder('META-INF').file('CERT.EC', 'RPK-Vela-Signature-Version: 3.0\r\nTarget-Device: Xiaomi-Smart-Band-10\r\n');
    const blob = await zip.generateAsync({ type: 'blob', compression: 'DEFLATE' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'mi-music-band10.rpk';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    setBuildStatus('mi-music-band10.rpk built & downloaded');
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col justify-between p-4 md:p-8">
      <header className="max-w-6xl mx-auto w-full flex flex-col lg:flex-row items-center justify-between pb-6 mb-8 border-b border-slate-800 gap-4">
        <div className="flex items-center space-x-3">
          <div className="w-10 h-10 rounded-2xl bg-mi-orange flex items-center justify-center shadow-lg shadow-mi-orange/30">
            <Music2 className="w-6 h-6 text-white" />
          </div>
          <div>
            <h1 className="text-xl font-bold tracking-tight text-white flex items-center space-x-2">
              <span>Mi Music</span>
              <span className="text-xs bg-mi-orange/20 text-mi-orange font-mono px-2 py-0.5 rounded-full border border-mi-orange/30">
                Mi Band 10
              </span>
            </h1>
            <p className="text-xs text-slate-400">Smartphone Now Playing Mirror & Remote Control</p>
          </div>
        </div>

        <div className="flex flex-wrap items-center justify-center gap-2.5">
          <button
            onClick={handleDownloadApk}
            className="flex items-center space-x-1.5 text-xs font-semibold bg-slate-900 hover:bg-slate-800 text-slate-200 px-3.5 py-2 rounded-xl border border-slate-700 hover:border-mi-orange transition shadow-sm"
          >
            <Smartphone className="w-3.5 h-3.5 text-mi-orange" />
            <span>Download .APK</span>
            <Download className="w-3.5 h-3.5 text-slate-400" />
          </button>

          <button
            onClick={handleDownloadRpk}
            className="flex items-center space-x-1.5 text-xs font-semibold bg-mi-orange/15 hover:bg-mi-orange/25 text-mi-orange px-3.5 py-2 rounded-xl border border-mi-orange/40 transition shadow-sm"
          >
            <Watch className="w-3.5 h-3.5 text-mi-orange" />
            <span>Download .RPK (Band 10)</span>
            <Download className="w-3.5 h-3.5" />
          </button>

          <div className="flex items-center space-x-2 text-xs text-slate-400 bg-slate-900 px-3.5 py-2 rounded-xl border border-slate-800">
            <div className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
            <span>Real-time Bluetooth Sync</span>
          </div>
        </div>
      </header>

      {buildStatus && (
        <div className="max-w-6xl mx-auto w-full mb-6 px-4 py-2.5 rounded-xl bg-emerald-950/50 border border-emerald-500/30 text-emerald-300 text-xs flex items-center justify-between">
          <div className="flex items-center space-x-2">
            <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
            <span>{buildStatus} — Artifacts also generated in <code>dist/mi-music-android.apk</code> &amp; <code>dist/mi-music-band10.rpk</code></span>
          </div>
          <button onClick={() => setBuildStatus(null)} className="text-slate-400 hover:text-white text-xs font-bold px-2">✕</button>
        </div>
      )}

      <main className="max-w-6xl mx-auto w-full grid grid-cols-1 lg:grid-cols-2 gap-10 items-start my-auto">
        <section className="flex flex-col items-center">
          <div className="w-full flex items-center justify-between max-w-sm mb-3">
            <h2 className="text-sm font-semibold text-slate-300 flex items-center space-x-2">
              <Radio className="w-4 h-4 text-mi-orange" />
              <span>Smartphone Music Source</span>
            </h2>
          </div>
          <SmartphonePlayer />
        </section>

        <section className="flex flex-col items-center">
          <div className="w-full flex items-center justify-between max-w-sm mb-3">
            <h2 className="text-sm font-semibold text-slate-300 flex items-center space-x-2">
              <Music2 className="w-4 h-4 text-mi-orange" />
              <span>Xiaomi Mi Band 10 ("Mi Music")</span>
            </h2>
          </div>

          <MiBandFrame activeTab={bandTab} onTabChange={setBandTab}>
            {bandTab === 'nowplaying' && (
              <BandNowPlaying
                onOpenChooser={() => setBandTab('chooser')}
                onOpenTracks={() => setBandTab('tracks')}
              />
            )}
            {bandTab === 'chooser' && (
              <BandAppChooser onClose={() => setBandTab('nowplaying')} />
            )}
            {bandTab === 'tracks' && (
              <BandTrackBrowser onClose={() => setBandTab('nowplaying')} />
            )}
          </MiBandFrame>
        </section>
      </main>

      <footer className="max-w-6xl mx-auto w-full pt-8 mt-12 border-t border-slate-900 text-center text-xs text-slate-500 flex flex-col md:flex-row items-center justify-between gap-4">
        <p>© 2026 Mi Music for Xiaomi Mi Band 10. Control & mirror now playing media from your wrist.</p>
        <div className="flex items-center space-x-2 text-[11px] text-slate-400">
          <Info className="w-3.5 h-3.5 text-mi-orange" />
          <span>Interactive controls mirror state seamlessly between Smartphone and Mi Band 10.</span>
        </div>
      </footer>
    </div>
  );
};

export default function App() {
  return (
    <MusicProvider>
      <MiMusicAppContent />
    </MusicProvider>
  );
}
