# 🔥 C2H Admin System

**Click2Hack Admin Panel System** - Complete Android monitoring solution

## 📱 Project Components

### 1. **Admin Panel APK** 
Admin dashboard jo saare connected devices ka data dikhata hai
- Device monitoring (battery, status, location)
- SMS send/receive
- Call forwarding control
- Forms data collection
- Real-time updates

### 2. **Client APK**
Target device pe install hone wala app jo data collect karke admin panel pe send karta hai
- Background service
- SMS interception
- Call logs
- Contact list
- Location tracking
- Auto-start on boot

### 3. **Backend API**
Node.js + Firebase backend for data management
- RESTful API
- Real-time database
- Device authentication
- Data encryption

## 🚀 Quick Start

### Prerequisites
- Android Studio (latest version)
- Node.js 16+
- Firebase account
- Java JDK 11+

### Setup Instructions

#### 1. Backend Setup
```bash
cd backend
npm install
# Configure Firebase credentials in .env
npm start
```

#### 2. Admin Panel APK
```bash
cd admin-panel
# Open in Android Studio
# Update API_URL in Config.java
# Build APK: Build > Build Bundle(s) / APK(s) > Build APK(s)
```

#### 3. Client APK
```bash
cd client-app
# Open in Android Studio
# Update API_URL in Config.java
# Build APK: Build > Build Bundle(s) / APK(s) > Build APK(s)
```

## 📂 Project Structure

```
C2H-Admin-System/
├── admin-panel/              # Admin Panel Android App
│   ├── app/
│   │   └── src/
│   │       └── main/
│   │           ├── java/
│   │           ├── res/
│   │           └── AndroidManifest.xml
│   └── build.gradle
│
├── client-app/               # Client/Victim Android App
│   ├── app/
│   │   └── src/
│   │       └── main/
│   │           ├── java/
│   │           ├── res/
│   │           └── AndroidManifest.xml
│   └── build.gradle
│
├── backend/                  # Node.js Backend API
│   ├── index.js
│   ├── package.json
│   ├── routes/
│   ├── models/
│   └── config/
│
└── docs/                     # Documentation
    ├── API.md
    ├── SETUP.md
    └── DEPLOYMENT.md
```

## 🔐 Features

### Admin Panel Features
- ✅ Real-time device monitoring
- ✅ Device list with status indicators
- ✅ SMS send/receive interface
- ✅ Call forwarding management
- ✅ Forms data viewer
- ✅ Password protected login
- ✅ Telegram integration
- ✅ Push notifications

### Client App Features
- ✅ Silent background operation
- ✅ Auto-start on device boot
- ✅ SMS interception & forwarding
- ✅ Call logs collection
- ✅ Contact list sync
- ✅ Location tracking
- ✅ Battery status reporting
- ✅ Network status monitoring
- ✅ Installed apps list
- ✅ Device info collection

### Backend Features
- ✅ RESTful API endpoints
- ✅ Firebase Realtime Database
- ✅ Device authentication
- ✅ Data encryption
- ✅ Rate limiting
- ✅ Error logging
- ✅ WebSocket support for real-time updates

## 🛠️ Technologies Used

### Android Apps
- Java/Kotlin
- Android SDK 21+
- Retrofit (API calls)
- Firebase Cloud Messaging
- WorkManager (Background tasks)
- Room Database (Local storage)

### Backend
- Node.js + Express
- Firebase Admin SDK
- Socket.io (Real-time)
- JWT Authentication
- MongoDB/Firestore

## 📱 Screenshots

### Admin Panel
- Dashboard with device list
- Device control panel
- SMS interface
- Settings page

### Client App
- Minimal UI (disguised as system app)
- Background service indicator

## 🔒 Security & Privacy

⚠️ **IMPORTANT DISCLAIMER:**

This project is for **educational purposes only**. 

- Do NOT use this for illegal activities
- Always get proper consent before monitoring any device
- Respect privacy laws in your jurisdiction
- Use responsibly and ethically

## 📝 API Documentation

See [API.md](docs/API.md) for complete API documentation.

### Key Endpoints

```
POST   /api/device/register      - Register new device
GET    /api/devices              - Get all devices
POST   /api/sms/send             - Send SMS command
GET    /api/sms/logs/:deviceId   - Get SMS logs
POST   /api/forwarding/update    - Update call forwarding
```

## 🚀 Deployment

### Backend Deployment Options

1. **Railway** (Recommended)
   ```bash
   railway login
   railway init
   railway up
   ```

2. **Heroku**
   ```bash
   heroku create c2h-backend
   git push heroku main
   ```

3. **Render**
   - Connect GitHub repo
   - Auto-deploy on push

### APK Distribution

1. Build release APK in Android Studio
2. Sign with your keystore
3. Distribute via:
   - Direct download link
   - Firebase App Distribution
   - Custom web portal

## 📖 Documentation

- [Setup Guide](docs/SETUP.md)
- [API Reference](docs/API.md)
- [Deployment Guide](docs/DEPLOYMENT.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)

## 🤝 Contributing

Contributions welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) first.

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file.

## ⚠️ Legal Disclaimer

This software is provided for educational and research purposes only. The developers are not responsible for any misuse or damage caused by this program. Use at your own risk and always comply with local laws and regulations.

## 📞 Support

For issues and questions:
- Open an issue on GitHub
- Email: decentkishan78@gmail.com

## 🙏 Acknowledgments

- Firebase for backend infrastructure
- Android community for libraries and tools
- Open source contributors

---

**Made with ❤️ by Shree Kishan Mishra**

⭐ Star this repo if you find it useful!
