import React from 'react';
import { useMusicStore } from '../store/musicStore';
import { MUSIC_APPS } from '../data/mockSongs';
import { audioEngine } from '../services/audioSimulator';
import { ArrowLeft, Check, Smartphone } from 'lucide-react';

export const BandAppChooser = ({ onClose }) => {
  const { activeAppId, selectMusicApp } = useMusicStore();

  return (
    <div className="flex-1 flex flex-col justify-between p-3 bg-black text-white relative">
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
          <span className="text-xs font-bold text-slate-200">Select App</span>
        </div>

        <p className="text-[9px] text-slate-400 mb-2 px-1">
          Control smartphone music player app from wrist:
        </p>

        {/* Music App Options */}
        <div className="space-y-1.5 max-h-[300px] overflow-y-auto pr-0.5">
          {MUSIC_APPS.map((app) => {
            const isSelected = activeAppId === app.id;
            return (
              <button
                key={app.id}
                onClick={() => {
                  audioEngine.playHapticClick();
                  selectMusicApp(app.id, 'Mi Band 10');
                  onClose();
                }}
                className={`w-full flex items-center justify-between p-2 rounded-xl text-left transition ${
                  isSelected
                    ? 'bg-slate-900 border border-mi-orange text-white'
                    : 'bg-slate-950/80 hover:bg-slate-900 text-slate-300 border border-slate-900'
                }`}
              >
                <div className="flex items-center space-x-2 truncate">
                  <div
                    className="w-6 h-6 rounded-lg flex items-center justify-center font-bold text-[10px] text-white shrink-0 shadow"
                    style={{ backgroundColor: app.color }}
                  >
                    {app.name[0]}
                  </div>
                  <span className="text-xs font-medium truncate">{app.name}</span>
                </div>
                {isSelected && <Check className="w-3.5 h-3.5 text-mi-orange shrink-0 ml-1" />}
              </button>
            );
          })}
        </div>
      </div>

      <div className="flex items-center justify-center text-[9px] text-slate-500 pt-2 border-t border-slate-900">
        <Smartphone className="w-3 h-3 mr-1 text-slate-600" />
        <span>Mi Band 10 Sync</span>
      </div>
    </div>
  );
};
