import { useState, useEffect, useCallback, createContext, useContext } from 'react';
import { INITIAL_SONGS, MUSIC_APPS, EQ_PRESETS } from '../data/mockSongs';

const MusicStoreContext = createContext(null);

export const MusicProvider = ({ children }) => {
  const [songs, setSongs] = useState(INITIAL_SONGS);
  const [currentSongId, setCurrentSongId] = useState(INITIAL_SONGS[0].id);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentTime, setCurrentTime] = useState(0);
  const [volume, setVolume] = useState(70);
  const [activeAppId, setActiveAppId] = useState('spotify');
  const [isConnected, setIsConnected] = useState(true);
  const [lastActionSource, setLastActionSource] = useState('Smartphone'); // 'Smartphone' or 'Mi Band 10'
  const [currentEq, setCurrentEq] = useState('bass'); // 'flat', 'bass', 'pop', 'vocal'
  const [sleepTimerMinutes, setSleepTimerMinutes] = useState(0); // 0 = off

  const currentSong = songs.find(s => s.id === currentSongId) || songs[0];
  const activeApp = MUSIC_APPS.find(a => a.id === activeAppId) || MUSIC_APPS[0];

  // Playback timer ticker
  useEffect(() => {
    let interval = null;
    if (isPlaying) {
      interval = setInterval(() => {
        setCurrentTime((prevTime) => {
          if (prevTime >= currentSong.duration) {
            nextSong();
            return 0;
          }
          return prevTime + 1;
        });
      }, 1000);
    } else {
      clearInterval(interval);
    }
    return () => clearInterval(interval);
  }, [isPlaying, currentSong.duration]);

  // Sleep timer ticker
  useEffect(() => {
    let timer = null;
    if (sleepTimerMinutes > 0 && isPlaying) {
      timer = setInterval(() => {
        setSleepTimerMinutes((prev) => {
          if (prev <= 1) {
            setIsPlaying(false);
            return 0;
          }
          return prev - 1;
        });
      }, 60000); // decrement every minute
    }
    return () => clearInterval(timer);
  }, [sleepTimerMinutes, isPlaying]);

  const toggleFavorite = useCallback((songId) => {
    setSongs(prevSongs => prevSongs.map(song => {
      if (song.id === (songId || currentSongId)) {
        return { ...song, isFavorite: !song.isFavorite };
      }
      return song;
    }));
  }, [currentSongId]);

  const playSong = useCallback((songId, source = 'Smartphone') => {
    setCurrentSongId(songId);
    setCurrentTime(0);
    setIsPlaying(true);
    setLastActionSource(source);
    const targetSong = songs.find(s => s.id === songId);
    if (targetSong && targetSong.appSource) {
      setActiveAppId(targetSong.appSource);
    }
  }, [songs]);

  const togglePlayPause = useCallback((source = 'Smartphone') => {
    setIsPlaying(prev => !prev);
    setLastActionSource(source);
  }, []);

  const nextSong = useCallback((source = 'Smartphone') => {
    const currentIndex = songs.findIndex(s => s.id === currentSongId);
    const nextIndex = (currentIndex + 1) % songs.length;
    setCurrentSongId(songs[nextIndex].id);
    setCurrentTime(0);
    setLastActionSource(source);
  }, [songs, currentSongId]);

  const previousSong = useCallback((source = 'Smartphone') => {
    const currentIndex = songs.findIndex(s => s.id === currentSongId);
    const prevIndex = (currentIndex - 1 + songs.length) % songs.length;
    setCurrentSongId(songs[prevIndex].id);
    setCurrentTime(0);
    setLastActionSource(source);
  }, [songs, currentSongId]);

  const seekTime = useCallback((time, source = 'Smartphone') => {
    setCurrentTime(Math.min(Math.max(0, time), currentSong.duration));
    setLastActionSource(source);
  }, [currentSong.duration]);

  const setVolumeLevel = useCallback((val, source = 'Smartphone') => {
    setVolume(Math.min(100, Math.max(0, val)));
    setLastActionSource(source);
  }, []);

  const selectMusicApp = useCallback((appId, source = 'Smartphone') => {
    setActiveAppId(appId);
    setLastActionSource(source);
  }, []);

  const setEqPreset = useCallback((eqId, source = 'Smartphone') => {
    setCurrentEq(eqId);
    setLastActionSource(source);
  }, []);

  const setSleepTimer = useCallback((mins, source = 'Smartphone') => {
    setSleepTimerMinutes(mins);
    setLastActionSource(source);
  }, []);

  const toggleConnection = useCallback(() => {
    setIsConnected(prev => !prev);
  }, []);

  const value = {
    songs,
    currentSong,
    isPlaying,
    currentTime,
    volume,
    activeApp,
    activeAppId,
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
  };

  return (
    <MusicStoreContext.Provider value={value}>
      {children}
    </MusicStoreContext.Provider>
  );
};

export const useMusicStore = () => {
  const context = useContext(MusicStoreContext);
  if (!context) {
    throw new Error('useMusicStore must be used within a MusicProvider');
  }
  return context;
};
