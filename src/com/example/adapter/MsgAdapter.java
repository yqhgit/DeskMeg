package com.example.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.example.bean.Msg;
import com.example.deskmsg.R;
import com.example.holder.MsgHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MsgAdapter extends BaseAdapter{
    private List<Msg> msglist;
    private Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}

}
