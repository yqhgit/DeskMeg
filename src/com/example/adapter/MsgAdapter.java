package com.example.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.example.activity.MainActivity;
import com.example.activity.TalkToActivity;
import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.deskmsg.R;
import com.example.deskmsg.R.color;
import com.example.holder.MsgHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MsgAdapter extends BaseAdapter{
    private List<AllContact> msglist;
    private Context context;
    private SparseBooleanArray booleanArray;
    public MsgAdapter(List<AllContact> msglist, Context context){
        this.context = context;
        this.msglist = msglist;
        booleanArray = new SparseBooleanArray();
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
			convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pressed_bg));
			holder = new MsgHolder(convertView);
			convertView.setTag(holder);
		}else
			holder = (MsgHolder) convertView.getTag();
		Log.e("check", "ingetview"+MainActivity.flag);
		//if(MainActivity.flag == 1){
		    Log.e("check", "in");
    		if(booleanArray.get(position)){
    		    Log.e("check", "true");
    		    convertView.setSelected(true);
    		    convertView.setPressed(true);
    		    }
    		    
		//}
		holder.setDate(msglist.get(position), context);
		return convertView;
	}
	
	public SparseBooleanArray getBooleanArray(){
	    return booleanArray;
	}
    
	public void changeState(int position){
	    selectView(position, !booleanArray.get(position));
	}
	
	public void selectView(int position, Boolean value){
	    if(value)
	        booleanArray.put(position, value);
	    else
	        booleanArray.delete(position);
	}
	public void removeAll(){
	    Log.e("remove", "remove");
	    booleanArray = new SparseBooleanArray();
	}
}
