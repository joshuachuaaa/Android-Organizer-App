package com.example.assignment1_fit2081_32781555.SMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String CATEGORY_KEY = "category";
    public static final String EVENT_KEY = "event";

    @Override
    public void onReceive(Context context, Intent intent){

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage currentMessage : messages) {

            String message = currentMessage.getDisplayMessageBody();
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);

            if (SMSFormat.isValid(CATEGORY_KEY, message)) {
                msgIntent.putExtra(CATEGORY_KEY, SMSFormat.extractData(message));
                context.sendBroadcast(msgIntent);
            } else if (SMSFormat.isValid(EVENT_KEY, message)) {
                msgIntent.putExtra(EVENT_KEY, SMSFormat.extractData(message));
                context.sendBroadcast(msgIntent);
            } else {
                invalidFormat(context);
            }
        }
    }

    private void invalidFormat(Context context){Toast.makeText(context,"Invalid Format, Please do 'Token : Data'",Toast.LENGTH_LONG).show();}


}