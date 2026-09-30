import React, { useState } from 'react';
import { useMusicStore } from '../store/musicStore';
import { formatTime, audioEngine } from '../services/audioSimulator';
import {
  Play,
  Pause,
  SkipBack,
  SkipForward,
  Volume2,
  Volume1,
  VolumeX,
  WifiOff,
  Sparkles,
  Zap,
  Sun,
  Disc,
  Flame,
  Radio,
  Grid,
  ListMusic,
  Heart,
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

export const BandNowPlaying = ({ onOpenChooser, onOpenTracks }) => {
  const {
    currentSong,
    isPlaying,
    currentTime,
    volume,
    activeApp,
    isConnected,
    sleepTimerMinutes,
    togglePlayPause,
    nextSong,
    previousSong,
    setVolumeLevel,
    toggleFavorite
  } = useMusicStore();

  const [showVolumeSlider, setShowVolumeSlider] = useState(false);
  const [showBandLyrics, setShowBandLyrics] = useState(false);

  const IconComp = COVER_ICONS[currentSong.coverIcon] || Disc;
  const progressPercent = (currentTime / currentSong.duration) * 100;

  const activeLyricIndex = currentSong.lyrics ? currentSong.lyrics.reduce((acc, line, idx) => {
    return currentTime >= line.time ? idx : acc;
  }, 0) : 0;

  if (!isConnected) {
    return (
      <div className="flex-1 flex flex-col items-center justify-center p-4 text-center">
        <WifiOff className="w-10 h-10 text-slate-600 mb-2 animate-bounce" />
        <p className="text-xs font-semibold text-slate-300">Phone Disconnected</p>
        <p className="text-[10px] text-slate-500 mt-1">Connect phone to stream music metadata to Mi Band 10</p>
      </div>
    );
  }

  return (
    <div className="flex-1 flex flex-col justify-between p-3.5 relative overflow-hidden">
      <div className="flex items-center justify-between text-[10px] text-slate-400 pb-1 border-b border-slate-900">
        <div className="flex items-center space-x-1.5">
          <div
            className="w-2 h-2 rounded-full"
            style={{ backgroundColor: activeApp.color }}
          />
          <span className="font-semibold text-slate-300 truncate max-w-[70px]">
            {activeApp.name}
          </span>
        </div>
        <div className="flex items-center space-x-1">
          {sleepTimerMinutes > 0 && (
            <span className="text-[8px] bg-purple-900/60 text-purple-300 px-1 rounded">
              {sleepTimerMinutes}m
            </span>
          )}
          <span className="font-mono text-[9px] text-slate-400">10:42</span>
        </div>
      </div>

      {!showBandLyrics ? (
        <div className="my-2 relative flex flex-col items-center">
          <div className="relative w-28 h-28 rounded-2xl overflow-hidden shadow-lg border border-slate-800 group">
            <div className={`w-full h-full bg-gradient-to-br ${currentSong.coverBg} flex flex-col items-center justify-center p-2 text-white`}>
              <div className="p-2.5 rounded-full bg-white/10 backdrop-blur-md mb-1 ring-1 ring-white/20">
                <IconComp className="w-8 h-8 text-white" />
              </div>
            </div>

            <button
              onClick={() => {
                audioEngine.playHapticClick();
                toggleFavorite(currentSong.id);
              }}
              className="absolute top-1.5 left-1.5 bg-black/60 backdrop-blur-md p-1 rounded-full text-slate-300 hover:text-rose-500 transition"
            >
              <Heart className={`w-3.5 h-3.5 ${currentSong.isFavorite ? 'text-rose-500 fill-current' : ''}`} />
            </button>

            {isPlaying && (
              <div className="absolute top-1.5 right-1.5 bg-black/60 backdrop-blur-md px-1.5 py-0.5 rounded-full flex items-center space-x-0.5">
                <div className="w-1 h-2 bg-mi-orange animate-pulse rounded-full" />
                <div className="w-1 h-3 bg-mi-orange animate-pulse rounded-full" />
                <div className="w-1 h-1.5 bg-mi-orange animate-pulse rounded-full" />
              </div>
            )}
          </div>
        </div>
      ) : (
        <div className="my-2 h-28 bg-slate-950 border border-slate-900 rounded-2xl p-2 flex flex-col justify-center items-center text-center overflow-hidden">
          <span className="text-[8px] uppercase font-bold text-mi-orange mb-1">Live Wrist Lyrics</span>
          <p className="text-[10px] text-white font-bold animate-pulse line-clamp-2 px-1">
            {currentSong.lyrics ? currentSong.lyrics[activeLyricIndex]?.text : "No lyrics available"}
          </p>
        </div>
      )}

      <div className="text-center px-1">
        <h3 className="text-xs font-bold text-white line-clamp-1 leading-snug">
          {currentSong.title}
        </h3>
        <p className="text-[10px] text-slate-400 line-clamp-1 mt-0.5">
          {currentSong.artist}
        </p>
      </div>

      <div className="my-1.5 px-1">
        <div className="w-full bg-slate-800 h-1.5 rounded-full overflow-hidden">
          <div
            className="bg-mi-orange h-full rounded-full transition-all duration-300"
            style={{ width: `${progressPercent}%` }}
          />
        </div>
        <div className="flex justify-between text-[9px] text-slate-500 mt-1 font-mono">
          <span>{formatTime(currentTime)}</span>
          <span>{formatTime(currentSong.duration)}</span>
        </div>
      </div>

      <div className="flex items-center justify-around my-1">
        <button
          onClick={() => {
            audioEngine.playHapticClick();
            previousSong('Mi Band 10');
          }}
          className="p-1.5 text-slate-300 hover:text-white active:scale-90 transition"
        >
          <SkipBack className="w-4 h-4" />
        </button>

        <button
          onClick={() => {
            audioEngine.playHapticClick();
            togglePlayPause('Mi Band 10');
          }}
          className="p-2.5 bg-mi-orange text-white rounded-full shadow-md active:scale-95 transition"
        >
          {isPlaying ? <Pause className="w-4 h-4 fill-current" /> : <Play className="w-4 h-4 fill-current ml-0.5" />}
        </button>

        <button
          onClick={() => {
            audioEngine.playHapticClick();
            nextSong('Mi Band 10');
          }}
          className="p-1.5 text-slate-300 hover:text-white active:scale-90 transition"
        >
          <SkipForward className="w-4 h-4" />
        </button>
      </div>

      <div className="flex items-center justify-between pt-1 border-t border-slate-900 text-slate-400">
        <button
          onClick={() => {
            audioEngine.playHapticClick();
            setShowVolumeSlider(!showVolumeSlider);
          }}
          className={`p-1 rounded flex items-center space-x-0.5 text-[9px] ${showVolumeSlider ? 'text-mi-orange' : 'hover:text-white'}`}
        >
          {volume === 0 ? <VolumeX className="w-3.5 h-3.5" /> : <Volume2 className="w-3.5 h-3.5" />}
          <span>{volume}%</span>
        </button>

        <button
          onClick={() => {
            audioEngine.playHapticClick();
            setShowBandLyrics(!showBandLyrics);
          }}
          className={`p-1 rounded flex items-center space-x-0.5 text-[9px] ${showBandLyrics ? 'text-mi-orange' : 'hover:text-white'}`}
          title="Toggle Lyrics"
        >
          <FileText className="w-3.5 h-3.5 text-mi-orange" />
        </button>

        <button
          onClick={() => {
            audioEngine.playHapticClick();
            onOpenChooser();
          }}
          className="p-1 hover:text-white rounded flex items-center space-x-0.5 text-[9px]"
          title="Switch App Source"
        >
          <Grid className="w-3.5 h-3.5 text-mi-orange" />
          <span>Apps</span>
        </button>

        <button
          onClick={() => {
            audioEngine.playHapticClick();
            onOpenTracks();
          }}
          className="p-1 hover:text-white rounded flex items-center space-x-0.5 text-[9px]"
          title="Track Library"
        >
          <ListMusic className="w-3.5 h-3.5 text-mi-orange" />
        </button>
      </div>

      {showVolumeSlider && (
        <div className="absolute inset-x-2 bottom-12 bg-slate-900/95 backdrop-blur-md border border-slate-800 rounded-xl p-2.5 flex flex-col items-center z-40 shadow-2xl animate-fade-in">
          <div className="flex items-center justify-between w-full mb-1">
            <span className="text-[10px] font-semibold text-slate-300">Band Volume</span>
            <button
              onClick={() => setShowVolumeSlider(false)}
              className="text-[10px] text-slate-500 hover:text-white font-bold px-1"
            >
              ✕
            </button>
          </div>
          <div className="flex items-center space-x-2 w-full">
            <Volume1 className="w-3.5 h-3.5 text-slate-400 shrink-0" />
            <input
              type="range"
              min="0"
              max="100"
              value={volume}
              onChange={(e) => setVolumeLevel(Number(e.target.value), 'Mi Band 10')}
              className="w-full h-1 bg-slate-800 rounded-lg appearance-none cursor-pointer accent-mi-orange"
            />
            <Volume2 className="w-3.5 h-3.5 text-slate-400 shrink-0" />
          </div>
        </div>
      )}
    </div>
  );
};
