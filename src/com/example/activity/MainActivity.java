package com.example.activity;

import java.util.List;

import com.example.adapter.MsgAdapter;
import com.example.bean.AllContact;
import com.example.db.dao.MsgDao;
import com.example.deskmsg.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView listview;
    private List<AllContact> list;
    Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		listview = (ListView) findViewById(R.id.listmsg);
		list = new MsgDao(context).getContactMsg();
		listview.setAdapter(new MsgAdapter(list,context));
		startservice();
		
	}
    void startservice(){
    	Intent intent = new Intent();
		intent.setClass(this, ReceiveMsg.class);
		startService(intent);
    }
}
