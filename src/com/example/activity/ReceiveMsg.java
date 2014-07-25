package com.example.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.bean.Msg;
import com.example.db.dao.MsgDao;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.ViewDebug.FlagToString;

public class ReceiveMsg extends Service {
    private ArrayList<Msg> arrayMsg = new ArrayList<Msg>();
    MessageReceive masReceive;
    static String TAG = "com.example.deskmsg";
    MessageReceive mReceive;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e(TAG, "in");
		mReceive = new MessageReceive();
		IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		intentFilter.setPriority(Integer.MAX_VALUE);
		registerReceiver(mReceive, intentFilter);
		Log.e(TAG, "reg");
	}

	@Override
    public void onDestroy() {
        // TODO Auto-generated method stub
	    unregisterReceiver(mReceive);
        super.onDestroy();
    }

    @Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isfront(){
		ActivityManager am = (ActivityManager) this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
//		List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
//		if(list.size() == 0)
//		    return false;
//		for(ActivityManager.RunningAppProcessInfo process:list){
//			if(process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
//					&&process.processName.equals("com.example.deskmsg")){
//				Log.e("activity", "front");
//				return true;
//			}
//		}
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		 if(list != null && list.size() > 0){  
		        ComponentName component = list.get(0).topActivity;  
		        if("com.example.activity.MsgActivity".equals(component.getClassName())){  
		            return true;  
		            }  
		        } 
		return false;
	}
    public class MessageReceive extends BroadcastReceiver{
        public String content = "android.provider.Telephony.SMS_RECEIVED";
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(content.equals(intent.getAction())){
				Log.e(TAG, "get");
				Bundle bundle = intent.getExtras();
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage smsMessage[] = new SmsMessage[pdus.length];
				System.out.println("length="+pdus.length);
				for (int i = 0; i < pdus.length; i++) {
					smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					String content = smsMessage[i].getDisplayMessageBody();
					System.out.println(i+content);
					String from = smsMessage[i].getDisplayOriginatingAddress();
					String date = String.format("%2d-%2d %02d:%02d:%02d", 
							Calendar.getInstance().get(Calendar.MONTH),
							Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
							Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
							Calendar.getInstance().get(Calendar.MINUTE),
							Calendar.getInstance().get(Calendar.SECOND));
					Msg msg = new Msg(date, from, content,false);
					arrayMsg.add(msg);
				}
			}
			if(!MsgActivity.foreground){
				Log.e("result", "foreground");
				Intent intentmsg = new Intent();
				intentmsg.setClass(context, MsgActivity.class);
				intentmsg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentmsg);
				}
			else{
				Intent intent1 = new Intent("com.example.communication.RECEIVER");
				sendBroadcast(intent1);
			}
		}
    }
}
