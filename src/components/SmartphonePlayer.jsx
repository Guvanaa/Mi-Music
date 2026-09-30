import React, { useState } from 'react';
import { useMusicStore } from '../store/musicStore';
import { formatTime, audioEngine } from '../services/audioSimulator';
import { MUSIC_APPS, EQ_PRESETS } from '../data/mockSongs';
import {
  Play,
  Pause,
  SkipBack,
  SkipForward,
  Volume2,
  VolumeX,
  Wifi,
  WifiOff,
  Smartphone,
  Music,
  Radio,
  Disc,
  Sparkles,
  Zap,
  Sun,
  Flame,
  CheckCircle2,
  Watch,
  Heart,
  Sliders,
  Moon,
  FileText
} from 'lucide-react';

const COVER_ICONS = {
  Sparkles: Sparkles,
  Zap: Zap,
  Sun: Sun,
  Disc: Disc,
  Flame: Flame,
  Radio: Radio
};

export const SmartphonePlayer = () => {
  const {
    songs,
    currentSong,
    isPlaying,
    currentTime,
    volume,
    activeApp,
    isConnected,
    lastActionSource,
    currentEq,
    sleepTimerMinutes,
    playSong,
    togglePlayPause,
    nextSong,
    previousSong,
    seekTime,
    setVolumeLevel,
    selectMusicApp,
    setEqPreset,
    setSleepTimer,
    toggleFavorite,
    toggleConnection
  } = useMusicStore();

  const [showLyrics, setShowLyrics] = useState(false);

  const IconComp = COVER_ICONS[currentSong.coverIcon] || Disc;

  // Get current active line of lyrics based on time
  const activeLyricIndex = currentSong.lyrics ? currentSong.lyrics.reduce((acc, line, idx) => {
    return currentTime >= line.time ? idx : acc;
  }, 0) : 0;

  return (
    <div className="w-full max-w-sm mx-auto bg-slate-900 border border-slate-800 rounded-3xl p-5 shadow-2xl flex flex-col justify-between relative overflow-hidden">
      {/* Top Phone Header / Notch & Status */}
      <div>
        <div className="flex items-center justify-between pb-3 mb-4 border-b border-slate-800 text-xs text-slate-400">
          <div className="flex items-center space-x-2">
            <Smartphone className="w-4 h-4 text-mi-orange" />
            <span className="font-medium text-slate-200">Xiaomi Smartphone</span>
          </div>
          <button
            onClick={toggleConnection}
            className={`flex items-center space-x-1.5 px-2.5 py-1 rounded-full text-xs font-semibold transition ${
              isConnected
                ? 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/30'
                : 'bg-rose-500/10 text-rose-400 border border-rose-500/30'
            }`}
          >
            {isConnected ? (
              <>
                <Wifi className="w-3.5 h-3.5" />
                <span>Band Connected</span>
              </>
            ) : (
              <>
                <WifiOff className="w-3.5 h-3.5" />
                <span>Disconnected</span>
              </>
            )}
          </button>
        </div>

        {/* Sync Indicator Banner */}
        <div className="flex items-center justify-between bg-slate-800/80 backdrop-blur-md rounded-xl p-2.5 mb-4 border border-slate-700/50">
          <div className="flex items-center space-x-2 text-xs text-slate-300">
            <Watch className="w-4 h-4 text-mi-orange animate-pulse" />
            <span>Mi Band 10 Mirroring:</span>
          </div>
          <span className="text-xs font-semibold px-2 py-0.5 rounded bg-slate-700 text-slate-200">
            Active ({lastActionSource})
          </span>
        </div>

        {/* Music App Source Selector */}
        <div className="mb-4">
          <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2 block">
            Music Service Source
          </label>
          <div className="grid grid-cols-4 gap-2">
            {MUSIC_APPS.map((app) => {
              const isSelected = activeApp.id === app.id;
              return (
                <button
                  key={app.id}
                  onClick={() => {
                    audioEngine.playHapticClick();
                    selectMusicApp(app.id, 'Smartphone');
                  }}
                  className={`flex flex-col items-center p-2 rounded-xl border text-xs transition ${
                    isSelected
                      ? 'bg-slate-800 border-mi-orange text-white ring-1 ring-mi-orange'
                      : 'bg-slate-900/50 border-slate-800 text-slate-400 hover:border-slate-700 hover:text-slate-200'
                  }`}
                >
                  <div
                    className="w-7 h-7 rounded-lg flex items-center justify-center font-bold text-white mb-1 shadow-sm"
                    style={{ backgroundColor: app.color }}
                  >
                    {app.name[0]}
                  </div>
                  <span className="truncate w-full text-center text-[10px]">{app.name}</span>
                </button>
              );
            })}
          </div>
        </div>

        {/* Convenient Toolbar (EQ, Sleep Timer, Lyrics Toggle) */}
        <div className="grid grid-cols-3 gap-2 mb-4 bg-slate-950/50 p-2 rounded-xl border border-slate-800 text-xs">
          {/* EQ Preset Selector */}
          <div className="flex flex-col">
            <span className="text-[9px] text-slate-500 uppercase font-semibold flex items-center mb-1">
              <Sliders className="w-3 h-3 mr-1 text-mi-orange" /> EQ Preset
            </span>
            <select
              value={currentEq}
              onChange={(e) => setEqPreset(e.target.value, 'Smartphone')}
              className="bg-slate-900 border border-slate-700 text-slate-200 rounded px-1.5 py-0.5 text-xs focus:outline-none"
            >
              {EQ_PRESETS.map(eq => (
                <option key={eq.id} value={eq.id}>{eq.name}</option>
              ))}
            </select>
          </div>

          {/* Sleep Timer */}
          <div className="flex flex-col">
            <span className="text-[9px] text-slate-500 uppercase font-semibold flex items-center mb-1">
              <Moon className="w-3 h-3 mr-1 text-purple-400" /> Sleep Timer
            </span>
            <select
              value={sleepTimerMinutes}
              onChange={(e) => setSleepTimer(Number(e.target.value), 'Smartphone')}
              className="bg-slate-900 border border-slate-700 text-slate-200 rounded px-1.5 py-0.5 text-xs focus:outline-none"
            >
              <option value={0}>Off</option>
              <option value={15}>15 Min</option>
              <option value={30}>30 Min</option>
              <option value={60}>60 Min</option>
            </select>
          </div>

          {/* Synced Lyrics Toggle */}
          <div className="flex flex-col justify-end">
            <button
              onClick={() => setShowLyrics(!showLyrics)}
              className={`w-full py-1 px-2 rounded flex items-center justify-center space-x-1 font-semibold text-xs border transition ${
                showLyrics
                  ? 'bg-mi-orange/20 border-mi-orange text-mi-orange'
                  : 'bg-slate-900 border-slate-800 text-slate-400 hover:text-slate-200'
              }`}
            >
              <FileText className="w-3 h-3" />
              <span>Lyrics</span>
            </button>
          </div>
        </div>

        {/* Album Artwork or Synced Lyrics Display */}
        <div className="relative aspect-square w-full rounded-2xl overflow-hidden mb-4 shadow-xl border border-slate-800">
          {!showLyrics ? (
            <div className={`w-full h-full bg-gradient-to-br ${currentSong.coverBg} flex flex-col items-center justify-center p-6 text-white relative`}>
              <div className="absolute inset-0 bg-black/20 backdrop-blur-[2px]" />
              <div className="relative z-10 flex flex-col items-center text-center">
                <div className="p-4 rounded-full bg-white/10 backdrop-blur-md mb-3 ring-1 ring-white/20">
                  <IconComp className="w-12 h-12 text-white/90" />
                </div>
                <span className="text-xs font-semibold uppercase tracking-widest text-white/70 mb-1">
                  {currentSong.album}
                </span>
                <div className="flex items-center space-x-2">
                  <h2 className="text-lg font-bold text-white line-clamp-1">{currentSong.title}</h2>
                  <button
                    onClick={() => toggleFavorite(currentSong.id)}
                    className="text-slate-300 hover:text-rose-500 transition"
                  >
                    <Heart className={`w-5 h-5 ${currentSong.isFavorite ? 'text-rose-500 fill-current' : ''}`} />
                  </button>
                </div>
                <p className="text-sm text-white/80">{currentSong.artist}</p>
              </div>
              <div
                className="absolute top-3 right-3 px-2 py-1 rounded-md text-[10px] font-bold text-white shadow"
                style={{ backgroundColor: activeApp.color }}
              >
                {activeApp.name}
              </div>
            </div>
          ) : (
            <div className="w-full h-full bg-slate-950 p-4 flex flex-col justify-center items-center text-center overflow-y-auto space-y-3">
              <span className="text-[10px] font-bold uppercase text-mi-orange tracking-wider">Synced Live Lyrics</span>
              {currentSong.lyrics ? (
                currentSong.lyrics.map((line, idx) => (
                  <p
                    key={idx}
                    className={`text-xs transition-all duration-300 font-medium ${
                      idx === activeLyricIndex
                        ? 'text-white text-sm font-bold scale-105 text-mi-orange'
                        : 'text-slate-600'
                    }`}
                  >
                    {line.text}
                  </p>
                ))
              ) : (
                <p className="text-xs text-slate-500">No lyrics available for this track.</p>
              )}
            </div>
          )}
        </div>

        {/* Song Info & Scrubber */}
        <div className="mb-4">
          <div className="flex justify-between items-center mb-1">
            <span className="text-xs text-slate-400">{formatTime(currentTime)}</span>
            <span className="text-xs text-slate-400">{formatTime(currentSong.duration)}</span>
          </div>
          <input
            type="range"
            min="0"
            max={currentSong.duration}
            value={currentTime}
            onChange={(e) => {
              seekTime(Number(e.target.value), 'Smartphone');
            }}
            className="w-full h-1.5 bg-slate-800 rounded-lg appearance-none cursor-pointer accent-mi-orange"
          />
        </div>

        {/* Player Controls */}
        <div className="flex items-center justify-between mb-6 px-4">
          <button
            onClick={() => {
              audioEngine.playHapticClick();
              previousSong('Smartphone');
            }}
            className="p-3 text-slate-300 hover:text-white hover:bg-slate-800 rounded-full transition"
          >
            <SkipBack className="w-6 h-6" />
          </button>

          <button
            onClick={() => {
              audioEngine.playHapticClick();
              togglePlayPause('Smartphone');
            }}
            className="p-4 bg-mi-orange hover:bg-orange-600 text-white rounded-full shadow-lg transition transform active:scale-95"
          >
            {isPlaying ? <Pause className="w-7 h-7 fill-current" /> : <Play className="w-7 h-7 fill-current ml-0.5" />}
          </button>

          <button
            onClick={() => {
              audioEngine.playHapticClick();
              nextSong('Smartphone');
            }}
            className="p-3 text-slate-300 hover:text-white hover:bg-slate-800 rounded-full transition"
          >
            <SkipForward className="w-6 h-6" />
          </button>
        </div>

        {/* Volume Control */}
        <div className="flex items-center space-x-3 bg-slate-950/60 p-2.5 rounded-xl border border-slate-800 mb-4">
          {volume === 0 ? (
            <VolumeX className="w-4 h-4 text-slate-400" />
          ) : (
            <Volume2 className="w-4 h-4 text-slate-400" />
          )}
          <input
            type="range"
            min="0"
            max="100"
            value={volume}
            onChange={(e) => setVolumeLevel(Number(e.target.value), 'Smartphone')}
            className="w-full h-1 bg-slate-800 rounded-lg appearance-none cursor-pointer accent-mi-orange"
          />
          <span className="text-xs text-slate-400 min-w-[2rem] text-right font-mono">{volume}%</span>
        </div>
      </div>

      {/* Playlist Preview */}
      <div>
        <h3 className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2 flex items-center justify-between">
          <span>Smartphone Music Library</span>
          <Music className="w-3.5 h-3.5 text-slate-500" />
        </h3>
        <div className="space-y-1.5 max-h-36 overflow-y-auto pr-1">
          {songs.map((song) => {
            const isSelected = song.id === currentSong.id;
            return (
              <button
                key={song.id}
                onClick={() => {
                  audioEngine.playHapticClick();
                  playSong(song.id, 'Smartphone');
                }}
                className={`w-full text-left p-2 rounded-xl flex items-center justify-between transition text-xs ${
                  isSelected
                    ? 'bg-mi-orange/15 border border-mi-orange/40 text-white'
                    : 'bg-slate-950/40 hover:bg-slate-800 text-slate-300 border border-transparent'
                }`}
              >
                <div className="truncate pr-2">
                  <div className="font-medium truncate flex items-center space-x-1">
                    <span>{song.title}</span>
                    {song.isFavorite && <Heart className="w-3 h-3 text-rose-500 fill-current shrink-0" />}
                  </div>
                  <div className="text-[10px] text-slate-400 truncate">{song.artist}</div>
                </div>
                {isSelected && <CheckCircle2 className="w-4 h-4 text-mi-orange shrink-0" />}
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
};
