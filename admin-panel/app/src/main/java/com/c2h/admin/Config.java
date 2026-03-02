package com.c2h.admin;

public class Config {
    
    // ⚠️ IMPORTANT: Update this with your backend URL
    // For local testing: http://10.0.2.2:3000 (Android Emulator)
    // For production: https://your-backend-url.com
    public static final String API_BASE_URL = "http://10.0.2.2:3000/api/";
    
    // SharedPreferences keys
    public static final String PREFS_NAME = "C2HAdminPrefs";
    public static final String KEY_AUTH_TOKEN = "auth_token";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    
    // Request codes
    public static final int REQUEST_DEVICE_CONTROL = 100;
    
    // Refresh intervals (milliseconds)
    public static final long REFRESH_INTERVAL = 5000; // 5 seconds
    
    // API endpoints
    public static final String ENDPOINT_LOGIN = "admin/login";
    public static final String ENDPOINT_DEVICES = "devices";
    public static final String ENDPOINT_DEVICE_UPDATE = "device/update";
    public static final String ENDPOINT_SMS_SEND = "sms/send";
    public static final String ENDPOINT_SMS_LOGS = "sms/logs/";
    public static final String ENDPOINT_FORWARDING_UPDATE = "forwarding/update";
    public static final String ENDPOINT_FORMS = "forms/";
    public static final String ENDPOINT_CONTACTS = "contacts/";
    public static final String ENDPOINT_CALL_LOGS = "calls/logs/";
}
