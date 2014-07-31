package com.example.holder;

import com.example.bean.Msg;
import com.example.deskmsg.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MsgHolder {
    private TextView msgoutline;
    private TextView contacter;
    private TextView date;
    private ImageView photo;
    public MsgHolder(View view){
    	msgoutline = (TextView) view.findViewById(R.id.msgoutline);
    	contacter = (TextView) view.findViewById(R.id.contacter);
    	date = (TextView) view.findViewById(R.id.msgdate);
    	photo = (ImageView) view .findViewById(R.id.photo);
    }
    /*
     * context用户获取图片资源
     */
    public void setDate(Msg msg, Context context){
    	contacter.setText(msg.getDisplayName());
    	msgoutline.setText(msg.getContent());
    	date.setText(msg.getDate());
    	
    }
}
