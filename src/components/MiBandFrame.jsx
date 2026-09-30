import React from 'react';

export const MiBandFrame = ({ children, activeTab, onTabChange }) => {
  return (
    <div className="flex flex-col items-center select-none">
      {/* Strap Top */}
      <div className="w-24 h-16 bg-gradient-to-b from-slate-900 to-slate-800 rounded-t-3xl border-t border-x border-slate-700/50 shadow-inner flex items-center justify-center">
        <div className="w-12 h-1 bg-slate-700/50 rounded-full" />
      </div>

      {/* Mi Band 10 Body / Bezel */}
      <div className="relative w-[210px] h-[480px] bg-black rounded-[52px] p-[10px] shadow-[0_25px_60px_-15px_rgba(0,0,0,0.9)] border-[4px] border-slate-700 ring-1 ring-slate-800 flex flex-col items-center justify-between overflow-hidden">

        <div className="absolute inset-0 rounded-[48px] border border-white/10 pointer-events-none z-20" />
        <div className="absolute top-0 left-0 right-0 h-16 bg-gradient-to-b from-white/5 to-transparent rounded-t-[48px] pointer-events-none z-20" />

        <div className="w-full h-full bg-black rounded-[42px] overflow-hidden flex flex-col justify-between relative z-10 font-sans text-white border border-slate-900">

          <div className="flex-1 overflow-hidden relative flex flex-col">
            {children}
          </div>

          <div className="h-10 bg-black/90 backdrop-blur-md border-t border-slate-900/80 flex items-center justify-around px-3 z-30 shrink-0">
            <button
              onClick={() => onTabChange('nowplaying')}
              className={`flex flex-col items-center transition ${
                activeTab === 'nowplaying' ? 'text-mi-orange' : 'text-slate-500 hover:text-slate-300'
              }`}
            >
              <span className="text-[10px] font-bold tracking-tight">PLAYER</span>
              {activeTab === 'nowplaying' && <div className="w-1 h-1 bg-mi-orange rounded-full mt-0.5" />}
            </button>

            <button
              onClick={() => onTabChange('chooser')}
              className={`flex flex-col items-center transition ${
                activeTab === 'chooser' ? 'text-mi-orange' : 'text-slate-500 hover:text-slate-300'
              }`}
            >
              <span className="text-[10px] font-bold tracking-tight">APPS</span>
              {activeTab === 'chooser' && <div className="w-1 h-1 bg-mi-orange rounded-full mt-0.5" />}
            </button>

            <button
              onClick={() => onTabChange('tracks')}
              className={`flex flex-col items-center transition ${
                activeTab === 'tracks' ? 'text-mi-orange' : 'text-slate-500 hover:text-slate-300'
              }`}
            >
              <span className="text-[10px] font-bold tracking-tight">MUSIC</span>
              {activeTab === 'tracks' && <div className="w-1 h-1 bg-mi-orange rounded-full mt-0.5" />}
            </button>
          </div>
        </div>
      </div>

      {/* Strap Bottom */}
      <div className="w-24 h-16 bg-gradient-to-t from-slate-900 to-slate-800 rounded-b-3xl border-b border-x border-slate-700/50 shadow-inner flex items-center justify-center">
        <div className="w-12 h-1 bg-slate-700/50 rounded-full" />
      </div>
    </div>
  );
};
