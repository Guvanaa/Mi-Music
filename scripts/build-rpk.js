import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { execSync } from 'child_process';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const projectRoot = path.join(__dirname, '..');
const watchAppDir = path.join(projectRoot, 'watch-app');
const distDir = path.join(projectRoot, 'dist');
const rpkFilePath = path.join(distDir, 'mi-music-band10.rpk');

console.log('🚀 Packaging Xiaomi Mi Band 10 Native Firmware/App Package (.rpk)...');

if (!fs.existsSync(distDir)) {
  fs.mkdirSync(distDir, { recursive: true });
}

// Remove old RPK if exists
if (fs.existsSync(rpkFilePath)) {
  fs.unlinkSync(rpkFilePath);
}

// Create valid ZIP archive for .rpk firmware installer format
try {
  execSync(`cd "${watchAppDir}" && zip -r "${rpkFilePath}" app.json app.js page/index.js`, {
    stdio: 'inherit'
  });
  console.log(`\n✅ Successfully generated valid Xiaomi Mi Band 10 .rpk package ZIP:`);
  console.log(`   📦 ${rpkFilePath} (${fs.statSync(rpkFilePath).size} bytes)`);
} catch (err) {
  console.error('❌ Failed to create .rpk zip file:', err);
  process.exit(1);
}
