import React, { useState } from 'react';
import { MusicProvider } from './store/musicStore';
import { SmartphonePlayer } from './components/SmartphonePlayer';
import { MiBandFrame } from './components/MiBandFrame';
import { BandNowPlaying } from './components/BandNowPlaying';
import { BandAppChooser } from './components/BandAppChooser';
import { BandTrackBrowser } from './components/BandTrackBrowser';
import { Music2, Radio, Info } from 'lucide-react';

const MiMusicAppContent = () => {
  const [bandTab, setBandTab] = useState('nowplaying');

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col justify-between p-4 md:p-8">
      {/* Top Navigation / Brand Banner */}
      <header className="max-w-6xl mx-auto w-full flex flex-col md:flex-row items-center justify-between pb-6 mb-8 border-b border-slate-800 gap-4">
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

        <div className="flex items-center space-x-4 text-xs text-slate-400 bg-slate-900 px-4 py-2 rounded-2xl border border-slate-800">
          <div className="flex items-center space-x-1.5">
            <div className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
            <span>Real-time Bluetooth Sync</span>
          </div>
        </div>
      </header>

      {/* Main Dual Device View */}
      <main className="max-w-6xl mx-auto w-full grid grid-cols-1 lg:grid-cols-2 gap-10 items-start my-auto">

        {/* Left Column: Smartphone App */}
        <section className="flex flex-col items-center">
          <div className="w-full flex items-center justify-between max-w-sm mb-3">
            <h2 className="text-sm font-semibold text-slate-300 flex items-center space-x-2">
              <Radio className="w-4 h-4 text-mi-orange" />
              <span>Smartphone Music Source</span>
            </h2>
          </div>
          <SmartphonePlayer />
        </section>

        {/* Right Column: Xiaomi Mi Band 10 Display Mirror */}
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

      {/* Footer Info */}
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
