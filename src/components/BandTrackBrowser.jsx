import React from 'react';
import { useMusicStore } from '../store/musicStore';
import { audioEngine } from '../services/audioSimulator';
import { ArrowLeft, Play, Music, Volume2 } from 'lucide-react';

export const BandTrackBrowser = ({ onClose }) => {
  const { songs, currentSong, playSong } = useMusicStore();

  return (
    <div className="flex-1 flex flex-col justify-between p-3 bg-black text-white">
      {/* Header */}
      <div>
        <div className="flex items-center space-x-2 border-b border-slate-900 pb-2 mb-2">
          <button
            onClick={() => {
              audioEngine.playHapticClick();
              onClose();
            }}
            className="p-1 hover:bg-slate-900 rounded-full text-slate-400 hover:text-white"
          >
            <ArrowLeft className="w-4 h-4" />
          </button>
          <span className="text-xs font-bold text-slate-200">Phone Tracks</span>
        </div>

        {/* Track List */}
        <div className="space-y-1.5 max-h-[310px] overflow-y-auto pr-0.5">
          {songs.map((song) => {
            const isSelected = currentSong.id === song.id;
            return (
              <button
                key={song.id}
                onClick={() => {
                  audioEngine.playHapticClick();
                  playSong(song.id, 'Mi Band 10');
                  onClose();
                }}
                className={`w-full text-left p-2 rounded-xl flex items-center justify-between transition ${
                  isSelected
                    ? 'bg-mi-orange/20 border border-mi-orange text-white'
                    : 'bg-slate-950/80 hover:bg-slate-900 text-slate-300 border border-slate-900'
                }`}
              >
                <div className="truncate pr-1">
                  <div className="text-[11px] font-bold truncate leading-tight">{song.title}</div>
                  <div className="text-[9px] text-slate-400 truncate mt-0.5">{song.artist}</div>
                </div>
                {isSelected ? (
                  <div className="w-2 h-2 rounded-full bg-mi-orange animate-ping shrink-0" />
                ) : (
                  <Play className="w-3 h-3 text-slate-600 shrink-0 ml-1" />
                )}
              </button>
            );
          })}
        </div>
      </div>

      <div className="flex items-center justify-center text-[9px] text-slate-500 pt-1 border-t border-slate-900">
        <Music className="w-3 h-3 mr-1 text-slate-600" />
        <span>Select to play on phone</span>
      </div>
    </div>
  );
};
