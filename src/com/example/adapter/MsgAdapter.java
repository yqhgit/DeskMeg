package com.example.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.example.activity.TalkToActivity;
import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.deskmsg.R;
import com.example.holder.MsgHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MsgAdapter extends BaseAdapter{
    private List<AllContact> msglist;
    private Context context;
    public MsgAdapter(List<AllContact> msglist, Context context){
        this.context = context;
        this.msglist = msglist;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msglist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msglist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MsgHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.items, null);
			holder = new MsgHolder(convertView);
			convertView.setTag(holder);
		}else
			holder = (MsgHolder) convertView.getTag();
		holder.setDate(msglist.get(position), context);
		final int index = position;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("allcontact", msglist.get(index));
				intent.putExtra("bundle", bundle);
				intent.setClass(context, TalkToActivity.class);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

}
