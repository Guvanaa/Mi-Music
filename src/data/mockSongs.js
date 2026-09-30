export const INITIAL_SONGS = [
  {
    id: 'song-1',
    title: 'Midnight City',
    artist: 'M83',
    album: 'Hurry Up, We\'re Dreaming',
    duration: 243,
    appSource: 'spotify',
    coverBg: 'from-purple-900 via-indigo-900 to-black',
    coverIcon: 'Sparkles',
    accentColor: '#8B5CF6',
    isFavorite: true,
    lyrics: [
      { time: 0, text: "Waiting in a car..." },
      { time: 12, text: "Waiting for a ride in the dark..." },
      { time: 24, text: "The night city is an execution grid..." },
      { time: 38, text: "The city is my church, it keeps me sane..." },
      { time: 55, text: "Until the light takes us..." },
      { time: 80, text: "Midnight city, burning bright!" }
    ]
  },
  {
    id: 'song-2',
    title: 'Blinding Lights',
    artist: 'The Weeknd',
    album: 'After Hours',
    duration: 200,
    appSource: 'spotify',
    coverBg: 'from-red-900 via-rose-950 to-black',
    coverIcon: 'Zap',
    accentColor: '#EF4444',
    isFavorite: false,
    lyrics: [
      { time: 0, text: "Yeah..." },
      { time: 10, text: "I've been tryin' to call..." },
      { time: 22, text: "I'm on my own now..." },
      { time: 35, text: "I said, ooh, I'm blinded by the lights!" }
    ]
  },
  {
    id: 'song-3',
    title: 'As It Was',
    artist: 'Harry Styles',
    album: 'Harry\'s House',
    duration: 167,
    appSource: 'apple',
    coverBg: 'from-blue-900 via-sky-950 to-black',
    coverIcon: 'Sun',
    accentColor: '#3B82F6',
    isFavorite: true,
    lyrics: [
      { time: 0, text: "Come on, Harry, we wanna say goodnight to you!" },
      { time: 12, text: "Holdin' me back..." },
      { time: 28, text: "You know it's not the same as it was!" }
    ]
  },
  {
    id: 'song-4',
    title: 'Levitating',
    artist: 'Dua Lipa',
    album: 'Future Nostalgia',
    duration: 203,
    appSource: 'metrolist',
    coverBg: 'from-pink-900 via-fuchsia-950 to-black',
    coverIcon: 'Disc',
    accentColor: '#EC4899',
    isFavorite: false,
    lyrics: [
      { time: 0, text: "If you wanna run away with me, I know a galaxy..." },
      { time: 15, text: "I got you, moonlight, you're my starlight..." },
      { time: 30, text: "I'm levitating!" }
    ]
  },
  {
    id: 'song-5',
    title: 'Starboy',
    artist: 'The Weeknd ft. Daft Punk',
    album: 'Starboy',
    duration: 230,
    appSource: 'spotify',
    coverBg: 'from-amber-900 via-orange-950 to-black',
    coverIcon: 'Flame',
    accentColor: '#F59E0B',
    isFavorite: true,
    lyrics: [
      { time: 0, text: "I'm tryna put you in the worst mood..." },
      { time: 18, text: "Look what you've done, I'm a motherfuckin' starboy!" }
    ]
  },
  {
    id: 'song-6',
    title: 'Get Lucky',
    artist: 'Daft Punk',
    album: 'Random Access Memories',
    duration: 248,
    appSource: 'apple',
    coverBg: 'from-yellow-900 via-amber-950 to-black',
    coverIcon: 'Radio',
    accentColor: '#EAB308',
    isFavorite: false,
    lyrics: [
      { time: 0, text: "Like the legend of the phoenix..." },
      { time: 20, text: "We've come too far to give up who we are..." },
      { time: 40, text: "We're up all night to get lucky!" }
    ]
  }
];

export const MUSIC_APPS = [
  { id: 'spotify', name: 'Spotify', color: '#1DB954', iconBg: 'bg-green-600' },
  { id: 'apple', name: 'Apple Music', color: '#FA233B', iconBg: 'bg-red-500' },
  { id: 'ytmusic', name: 'YouTube Music', color: '#FF0000', iconBg: 'bg-rose-600' },
  { id: 'metrolist', name: 'Metrolist', color: '#E11D48', iconBg: 'bg-rose-500' },
  { id: 'innertune', name: 'InnerTune', color: '#3B82F6', iconBg: 'bg-blue-600' },
  { id: 'vimusic', name: 'ViMusic', color: '#8B5CF6', iconBg: 'bg-purple-600' },
  { id: 'mi', name: 'Mi Player', color: '#FF6700', iconBg: 'bg-orange-500' }
];

export const EQ_PRESETS = [
  { id: 'flat', name: 'Flat', bass: 0, mid: 0, treble: 0 },
  { id: 'bass', name: 'Bass Boost', bass: 8, mid: 2, treble: 1 },
  { id: 'pop', name: 'Pop', bass: 3, mid: 6, treble: 4 },
  { id: 'vocal', name: 'Vocal', bass: 1, mid: 8, treble: 5 }
];
