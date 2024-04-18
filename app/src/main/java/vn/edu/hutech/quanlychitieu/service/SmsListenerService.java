package vn.edu.hutech.quanlychitieu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Calendar;
import java.util.Objects;

import vn.edu.hutech.quanlychitieu.model.Notification;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.model.WalletType;
import vn.edu.hutech.quanlychitieu.util.ResolveSMSUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class SmsListenerService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SQLiteUtil sqLiteUtil = new SQLiteUtil(context);
        if(Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
            Bundle data = intent.getExtras();
            assert data != null;
            Object[] pdus = (Object[]) data.get("pdus");
            assert pdus != null;
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String message = smsMessage.getMessageBody();
                String sender = smsMessage.getDisplayOriginatingAddress();

                if (ResolveSMSUtil.isBank(sender)) {
                    sqLiteUtil.insertNotification(new Notification(0, sender + " - " + message, Calendar.getInstance().getTime(), Notification.STATUS_UNSEEN));
                    int amount = ResolveSMSUtil.getAmountFromMessage(message);
                    if (amount != -1) {
                        if (amount < 0) {
                            sqLiteUtil.insertTransaction(new Transaction(Double.parseDouble(Integer.toString(amount)) , sqLiteUtil.getGroupByGroupName("Khoản chi khác"), message, Calendar.getInstance().getTime(), WalletType.BANK_CARD));
                        } else {
                            sqLiteUtil.insertTransaction(new Transaction(Double.parseDouble(Integer.toString(amount)), sqLiteUtil.getGroupByGroupName("Khoản thu khác"), message, Calendar.getInstance().getTime(), WalletType.BANK_CARD));
                        }
                    }
                }
            }
        }
    }
}