package com.example.holder;

import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.deskmsg.R;
import com.example.util.DateUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
    public void setDate(AllContact outline, Context context){
        Log.e("debug","set");
    	contacter.setText(outline.getDisplayName());
    	msgoutline.setText(outline.getLastmsg());
    	//date.setText(DateUtil.getOutlineListInfo(outline.getLastdate()));
    	date.setText(outline.getLastdate());
    	//设置图片的默认资源
    	if(outline.getPhotobitmap()!=null)
    	    photo.setBackgroundDrawable(new BitmapDrawable(outline.getPhotobitmap()));
    	else
    	    photo.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
    }
}
