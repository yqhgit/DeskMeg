package com.example.activity;

import java.util.List;

import com.example.adapter.MsgAdapter;
import com.example.bean.AllContact;
import com.example.db.dao.MsgDao;
import com.example.deskmsg.R;

import android.R.menu;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private ListView listview;
    private List<AllContact> list;
    Context context;
    private Dialog menu_dialog;
    private View v;
    private TextView detail;
    private TextView batch_manage;
    private TextView putTop;
    private TextView delete;
    private int index = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		initView();
		
		list = new MsgDao(context).getContactMsg();
		if(list.size()>0)
		    listview.setAdapter(new MsgAdapter(list,context));
		initListener();
		startservice();
	}
	private void initView() {
        // TODO Auto-generated method stub
	    listview = (ListView) findViewById(R.id.listmsg);
	    menu_dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
	    v = LayoutInflater.from(context).inflate(R.layout.listlongclick_menu, null);
	    detail = (TextView) v.findViewById(R.id.detial);
	    delete = (TextView) v.findViewById(R.id.delete);
	    putTop = (TextView) v.findViewById(R.id.putTop);
	    batch_manage = (TextView) v.findViewById(R.id.batchManage);
	    menu_dialog.setContentView(v);
    }
    void initListener(){
	    listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("allcontact", list.get(position));
                intent.putExtra("bundle", bundle);
                intent.setClass(context, TalkToActivity.class);
                context.startActivity(intent);
            }
        });
	    listview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                index = position;
                menu_dialog.show();
                return false;
            }
        });
	    delete.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
	}
    void startservice(){
    	Intent intent = new Intent();
		intent.setClass(this, ReceiveMsg.class);
		startService(intent);
    }
}
