package com.example.util;

import com.example.deskmsg.R;
import com.example.deskmsg.R.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtil {
    public static Bitmap processImage(Bitmap bitmap) {
        Bitmap bmp;
     
        bmp = Bitmap.createBitmap(bitmap.getWidth(), 
            bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //着色器
        BitmapShader shader = new BitmapShader(bitmap, 
            BitmapShader.TileMode.CLAMP, 
            BitmapShader.TileMode.CLAMP);
     
       //        float radius = Math.min(bitmap.getWidth(), 
       //            bitmap.getHeight()) / RADIUS_FACTOR;
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        //反锯齿
        paint.setAntiAlias(true);
        //设置灰色背景
        //参数不能从资源文件获取 如color.xxx
        paint.setColor(0xffEeEeEe);
        canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2,
                (bitmap.getWidth()/2)>=(bitmap.getHeight()/2)?bitmap.getHeight()/2:bitmap.getWidth()/2, paint);
        paint.setColor(Color.WHITE);
        //设置着色器
        paint.setShader(shader);
     
        RectF rect = new RectF(0, 0, 
            bitmap.getWidth(), bitmap.getHeight());
        //canvas.drawRoundRect(rect, radius, radius, paint);
        canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2,
                (bitmap.getWidth()/2)>=(bitmap.getHeight()/2)?bitmap.getHeight()/2:bitmap.getWidth()/2, paint);
        return bmp;
    }
    public static Bitmap convertFromDrawable(Drawable d){
        BitmapDrawable bd = (BitmapDrawable)d;
        return bd.getBitmap();
    }
    public static Drawable convertFromBitmap(Bitmap b){
        return new BitmapDrawable(b);
    }
    public static Drawable getRoundImgFromDrawable(Drawable drawable) {
        // TODO Auto-generated method stub
        return convertFromBitmap(processImage(convertFromDrawable(drawable)));
    }
}
