package com.example.activity;

import com.example.deskmsg.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
	}
    void startservice(){
    	Intent intent = new Intent();
		intent.setClass(this, ReceiveMsg.class);
		startService(intent);
    }
}
