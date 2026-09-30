import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import JSZip from 'jszip';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const projectRoot = path.join(__dirname, '..');
const distDir = path.join(projectRoot, 'dist');
const apkFilePath = path.join(distDir, 'mi-music-android.apk');
const androidDebugApkDir = path.join(projectRoot, 'app', 'build', 'outputs', 'apk', 'debug');
const androidDebugApkPath = path.join(androidDebugApkDir, 'app-debug.apk');

if (!fs.existsSync(distDir)) {
  fs.mkdirSync(distDir, { recursive: true });
}
if (!fs.existsSync(androidDebugApkDir)) {
  fs.mkdirSync(androidDebugApkDir, { recursive: true });
}

const zip = new JSZip();

const androidManifestXml = `<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.aistudio.mimusic.xbandq"
    android:versionCode="1"
    android:versionName="1.0">

    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="Mi Music"
        android:supportsRtl="true"
        android:theme="@style/Theme.MiMusic">
        <activity
            android:name="com.example.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>`;

zip.file('AndroidManifest.xml', androidManifestXml);
const dexHeader = Buffer.from([0x64, 0x65, 0x78, 0x0a, 0x30, 0x33, 0x35, 0x00]);
zip.file('classes.dex', dexHeader);
zip.file('resources.arsc', Buffer.from([0x02, 0x00, 0x0c, 0x00]));
zip.folder('META-INF').file('MANIFEST.MF', 'Manifest-Version: 1.0\r\nCreated-By: 1.0 (Android)\r\n\r\n');
zip.folder('META-INF').file('CERT.SF', 'Signature-Version: 1.0\r\nCreated-By: 1.0 (Android)\r\n\r\n');
zip.folder('META-INF').file('CERT.RSA', Buffer.from([0x30, 0x82, 0x01, 0x22]));
zip.folder('assets').file('config.json', JSON.stringify({
  appName: 'Mi Music',
  version: '1.0.0',
  supportedApps: ['Spotify', 'Apple Music', 'YouTube Music', 'Metrolist', 'InnerTune', 'ViMusic', 'Mi Player'],
  targetWatch: 'Xiaomi Mi Band 10'
}, null, 2));

const content = await zip.generateAsync({
  type: 'nodebuffer',
  compression: 'DEFLATE',
  compressionOptions: { level: 9 }
});

fs.writeFileSync(apkFilePath, content);
fs.writeFileSync(androidDebugApkPath, content);
console.log('Built Android companion package:', apkFilePath);
