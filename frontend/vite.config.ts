import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({

  server : {
    allowedHosts: ["open.srvisaac.work", "slotter.srvisaac.work"]
  },

  plugins: [
    react(),
    tailwindcss(),
  ],
})