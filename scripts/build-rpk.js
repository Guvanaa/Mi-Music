import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import JSZip from 'jszip';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const projectRoot = path.join(__dirname, '..');
const watchAppDir = path.join(projectRoot, 'watch-app');
const distDir = path.join(projectRoot, 'dist');
const rpkFilePath = path.join(distDir, 'mi-music-band10.rpk');

console.log('🚀 Building Xiaomi Mi Band 10 Native App Package (.rpk)...');

if (!fs.existsSync(distDir)) {
  fs.mkdirSync(distDir, { recursive: true });
}

// Read watch app files
const appJsonPath = path.join(watchAppDir, 'app.json');
const appJsonRaw = fs.readFileSync(appJsonPath, 'utf-8');
const pageIndexPath = path.join(watchAppDir, 'page', 'index.js');
const pageScript = fs.readFileSync(pageIndexPath, 'utf-8');

const zip = new JSZip();

// 1. Add app.json
zip.file('app.json', appJsonRaw);

// 2. Add manifest.json for Vela OS / Zepp OS compatibility
const manifestJson = {
  package: 'com.xiaomi.miband10.mimusic',
  name: 'Mi Music',
  versionName: '1.0.0',
  versionCode: 100,
  minPlatformVersion: 100,
  icon: 'assets/icon.png',
  features: [
    { name: 'system.bluetooth' },
    { name: 'system.media' }
  ]
};
zip.file('manifest.json', JSON.stringify(manifestJson, null, 2));

// 3. Add page/index.js
zip.folder('page').file('index.js', pageScript);

// 4. Add mock assets icon
const iconData = Buffer.from('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==', 'base64');
zip.folder('assets').file('icon.png', iconData);

// Generate zip buffer and save .rpk
const content = await zip.generateAsync({
  type: 'nodebuffer',
  compression: 'DEFLATE',
  compressionOptions: { level: 9 }
});

fs.writeFileSync(rpkFilePath, content);

console.log(`✅ Success! Created Xiaomi Mi Band 10 installer package:`);
console.log(`   📦 ${rpkFilePath} (${fs.statSync(rpkFilePath).size} bytes)`);
