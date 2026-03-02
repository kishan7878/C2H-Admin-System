# 🚀 Quick Build Guide - C2H Admin System

**Sabse fast tarike se APKs banane ka guide!**

---

## ⚡ Quick Start (5 Minutes)

### Step 1: Repository Clone Karo

```bash
git clone https://github.com/kishan7878/C2H-Admin-System.git
cd C2H-Admin-System
```

---

## 🔥 Backend Setup (2 Minutes)

### 1. Firebase Setup

1. [Firebase Console](https://console.firebase.google.com/) pe jao
2. New project banao: `c2h-system`
3. Firestore Database enable karo
4. Service Account key download karo

### 2. Backend Configure Karo

```bash
cd backend
npm install
cp .env.example .env
```

`.env` file edit karo aur Firebase credentials add karo.

### 3. Backend Start Karo

```bash
npm start
```

Backend chal gaya! ✅

---

## 📱 Admin Panel APK Build (1 Minute)

### 1. Android Studio Mein Open Karo

```
File > Open > C2H-Admin-System/admin-panel
```

### 2. API URL Update Karo

`app/src/main/java/com/c2h/admin/Config.java`:

```java
// Local testing ke liye
public static final String API_BASE_URL = "http://10.0.2.2:3000/api/";

// Production ke liye (backend deploy karne ke baad)
// public static final String API_BASE_URL = "https://your-backend.com/api/";
```

### 3. APK Build Karo

```
Build > Build Bundle(s) / APK(s) > Build APK(s)
```

APK location: `admin-panel/app/build/outputs/apk/debug/app-debug.apk`

---

## 📲 Client APK Build (1 Minute)

### 1. Android Studio Mein Open Karo (New Window)

```
File > Open > C2H-Admin-System/client-app
```

### 2. API URL Update Karo

`app/src/main/java/com/c2h/client/Config.java`:

```java
// Production backend URL use karo
public static final String API_BASE_URL = "https://your-backend.com/api/";
```

### 3. Release APK Build Karo

```
Build > Generate Signed Bundle / APK > APK
```

Ya debug APK ke liye:
```
Build > Build Bundle(s) / APK(s) > Build APK(s)
```

---

## ☁️ Backend Deploy Karo (Railway - Fastest)

### 1. Railway CLI Install Karo

```bash
npm i -g @railway/cli
```

### 2. Deploy Karo

```bash
cd backend
railway login
railway init
railway up
```

### 3. Environment Variables Add Karo

Railway dashboard pe jao aur ye variables add karo:
- `ADMIN_PASSWORD`
- `FIREBASE_PROJECT_ID`
- `FIREBASE_PRIVATE_KEY`
- `FIREBASE_CLIENT_EMAIL`
- `FIREBASE_DATABASE_URL`

Backend deploy ho gaya! URL milega: `https://your-project.railway.app`

---

## ✅ Final Steps

### 1. Admin Panel APK Update Karo

Backend deploy hone ke baad, Admin Panel ka `Config.java` update karo:

```java
public static final String API_BASE_URL = "https://your-project.railway.app/api/";
```

Phir se build karo.

### 2. Client APK Update Karo

Client App ka bhi `Config.java` update karo same URL se.

Phir se build karo.

---

## 🧪 Testing

### 1. Backend Test Karo

```bash
curl https://your-project.railway.app/health
```

### 2. Admin Panel Install Karo

```bash
adb install admin-panel/app/build/outputs/apk/debug/app-debug.apk
```

Login karo with password from `.env`

### 3. Client App Install Karo

Test device pe install karo aur permissions grant karo.

Device Admin Panel mein dikhna chahiye!

---

## 📂 Project Structure

```
C2H-Admin-System/
├── backend/              # Node.js API
│   ├── index.js         # Main server file
│   ├── package.json     # Dependencies
│   └── .env             # Configuration
│
├── admin-panel/         # Admin APK
│   └── app/
│       └── src/main/
│           └── java/com/c2h/admin/
│               ├── MainActivity.java
│               ├── LoginActivity.java
│               └── Config.java
│
├── client-app/          # Client APK
│   └── app/
│       └── src/main/
│           └── java/com/c2h/client/
│               ├── MainActivity.java
│               ├── receivers/
│               └── Config.java
│
└── docs/                # Documentation
    ├── SETUP.md
    ├── API.md
    └── DEPLOYMENT.md
```

---

## 🎯 Key Files to Modify

### Backend
- `backend/.env` - Configuration
- `backend/index.js` - API logic

### Admin Panel
- `admin-panel/app/src/main/java/com/c2h/admin/Config.java` - API URL
- `admin-panel/app/src/main/res/layout/*.xml` - UI customization

### Client App
- `client-app/app/src/main/java/com/c2h/client/Config.java` - API URL
- `client-app/app/src/main/AndroidManifest.xml` - Permissions

---

## 🔧 Common Issues

### Backend won't start
```bash
# Check if port 3000 is already in use
lsof -i :3000
# Kill the process if needed
kill -9 <PID>
```

### APK build fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew build
```

### Device not appearing in Admin Panel
1. Check backend logs
2. Verify API URL in client Config.java
3. Ensure client has internet connection
4. Check if all permissions granted

---

## 📚 Detailed Documentation

- **Complete Setup:** [docs/SETUP.md](docs/SETUP.md)
- **API Reference:** [docs/API.md](docs/API.md)
- **Deployment:** [docs/DEPLOYMENT.md](docs/DEPLOYMENT.md)

---

## 🎉 Done!

Aapka C2H Admin System ready hai!

**Admin Panel APK:** Device monitoring dashboard  
**Client APK:** Data collection app  
**Backend:** API server

---

## 📞 Support

Issues? Questions?

- GitHub Issues: [Open Issue](https://github.com/kishan7878/C2H-Admin-System/issues)
- Email: decentkishan78@gmail.com

---

**Happy Building! 🚀**
