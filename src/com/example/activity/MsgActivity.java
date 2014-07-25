package com.example.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.activity.ReceiveMsg.MessageReceive;
import com.example.bean.Msg;
import com.example.db.dao.MsgDao;
import com.example.deskmsg.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MsgActivity extends Activity{
    private TextView content;
    private int current = 0;
    private TextView from;
    private Button reply;
    private Button delete;
    private EditText editext;
    private Button send;
    private ArrayList<Msg> msgList;
    private Receive mReceive;
    public static Activity instance;
    public static boolean foreground = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.msgdialog);
		instance = this;
		foreground = true;
		getMsg();
		initview();
		initlistener();
		register();
		showSms();
	}
	private void initlistener(){
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new MsgDao(MsgActivity.this).delete(msgList.get(current-1).getId());
				if(msgList!=null && current<msgList.size())
					showSms();
				else
					MsgActivity.this.finish();
			}
		});
		reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout option = (LinearLayout) findViewById(R.id.option);
				LinearLayout replylayout = (LinearLayout) findViewById(R.id.replylayout);
				option.setVisibility(View.GONE);
				replylayout.setVisibility(View.VISIBLE);
			}
		});
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(editext.getText()))
				    sendmsssage();
			}
		});
	}
	protected void sendmsssage() {
		// TODO Auto-generated method stub
		String content = editext.getText().toString();
		String date = String.format("%2d-%2d %02d:%02d:%02d", 
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
				Calendar.getInstance().get(Calendar.MINUTE),
				Calendar.getInstance().get(Calendar.SECOND));
		String address = msgList.get(current-1).getAddress();
		SmsManager manager = SmsManager.getDefault();
		if(content.length()>70){
			List<String> dividelist = manager.divideMessage(content);
			for(String divide:dividelist){
				manager.sendTextMessage(address, null, divide, null, null);
			}
		}else
			manager.sendTextMessage(address, null, content, null, null);
		Toast.makeText(MsgActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
	}
	private void initview() {
		// TODO Auto-generated method stub
		content = (TextView) findViewById(R.id.content);
		from = (TextView) findViewById(R.id.theme);
		//optionlayout
		delete = (Button) findViewById(R.id.delete);
		reply = (Button) findViewById(R.id.reply);
		
		//replylayout
		editext= (EditText) findViewById(R.id.replycontent);
		send = (Button) findViewById(R.id.send);
	}
	//显示受到的短信
	private void showSms() {
		// TODO Auto-generated method stub
		if(msgList!=null && current<msgList.size()){
			System.out.println("current="+current+msgList.get(current).getContent());
			if(msgList.get(current).getDisplayName() == null)
			    from.setText(msgList.get(current).getAddress());
			else 
				from.setText(msgList.get(current).getDisplayName());
			content.setText(msgList.get(current).getCotent());
			//new MsgDao(this).upgradeisread(msgList.get(current).getId());
			current++;
		}else 
			this.finish();
		
	}
	public void getMsg(){
		msgList = (ArrayList<Msg>) new MsgDao(this).searchnewmsg();
	}
	public class Receive extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			getMsg();
			current = 0;
			showSms();
		}
		
	}
	void register(){
		mReceive = new Receive();
		IntentFilter intentFilter = new IntentFilter("com.example.communication.RECEIVER");
		registerReceiver(mReceive, intentFilter);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		foreground = false;
		super.onPause();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		foreground = false;
		super.onStop();
	}
	
}
