package com.example.smsreadsend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReciever extends BroadcastReceiver {
    public final String SMS_BUNDLE="pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();

        if(intent.getAction().equalsIgnoreCase("android provider.Telephony.SMS_RECIEVED")){
            if(bundle!=null){
                Object[] sms=(Object[]) bundle.get(SMS_BUNDLE);
                String smsMsg="";
                SmsMessage smsMessage;
                for(int i=0; i<sms.length; i++){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        String format = bundle.getString("format");
                        smsMessage= SmsMessage.createFromPdu((byte[]) sms[i],format);
                    }
                    else{
                        smsMessage= SmsMessage.createFromPdu((byte[]) sms[i]);

                    }

                    String msgBody = smsMessage.getMessageBody().toString();
                    String msgAddress = smsMessage.getOriginatingAddress();

                    smsMsg+="SMS from : " + msgAddress + "\n";
                    smsMsg+= msgBody+"\n";

                }
                Main_page inst= Main_page.Instance();
                inst.updateList(smsMsg);
            }
        }
    }
}