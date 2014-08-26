package com.example.holder;

import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.deskmsg.R;
import com.example.util.DateUtil;
import com.example.util.ImageUtil;

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
     * context�û���ȡͼƬ��Դ
     */
    @SuppressWarnings("deprecation")
    public void setDate(AllContact outline, Context context){
    	contacter.setText(outline.getDisplayName());
    	msgoutline.setText(outline.getLastmsg());
    	//date.setText(DateUtil.getOutlineListInfo(outline.getLastdate()));
    	date.setText(outline.getLastdate());
    	//����ͼƬ��Ĭ����Դ
    	if(outline.getPhotobitmap()!=null)
    	    
    	    photo.setBackgroundDrawable(new BitmapDrawable(ImageUtil.processImage(outline.getPhotobitmap())));
    	else{
    	    //photo.setBackgroundDrawable(
    	    //        ImageUtil.getRoundImgFromDrawable(context.getResources().getDrawable(R.drawable.ic_launcher)));
    	    photo.setImageBitmap(ImageUtil.processImage(ImageUtil.convertFromDrawable(context.getResources().getDrawable(R.drawable.ic_launcher))));
    	}
    }
}
