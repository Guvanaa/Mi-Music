/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        mi: {
          orange: '#FF6700',
          dark: '#111111',
          card: '#1C1C1E',
          accent: '#2C2C2E',
          green: '#34C759',
          blue: '#0A84FF',
          purple: '#AF52DE',
          spotify: '#1DB954',
          apple: '#FA233B',
          ytmusic: '#FF0000'
        }
      },
      borderRadius: {
        'band': '36px',
        'band-screen': '32px'
      }
    },
  },
  plugins: [],
}
